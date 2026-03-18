package cn.edu.tjufe.zql.service.impl;


import cn.edu.tjufe.zql.domain.dto.LogDTO;
import cn.edu.tjufe.zql.domain.dto.LogDeleteDTO;
import cn.edu.tjufe.zql.domain.entity.Log;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.LogVO;
import cn.edu.tjufe.zql.domain.vo.PageVO;
import cn.edu.tjufe.zql.mapper.LogMapper;
import cn.edu.tjufe.zql.service.ILogService;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/11
 * @github https://github.com/little-seven-thirty
 */
@Service("logService")
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {
    @Resource
    private LogMapper logMapper;

    @Override
    public PageVO searchLog(LogDTO logDTO, Long current, Long pageSize) {
        LambdaQueryWrapper<Log> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(logDTO)) {
            wrapper.like(StringUtils.isNotEmpty(logDTO.getIp()),Log::getIp, logDTO.getIp())
                    .like(StringUtils.isNotEmpty(logDTO.getModule()),Log::getModule, logDTO.getModule())
                    .like(StringUtils.isNotEmpty(logDTO.getUserName()),Log::getUsername, logDTO.getUserName())
                    .like(StringUtils.isNotEmpty(logDTO.getOperation()),Log::getOperation, logDTO.getOperation())
                    .eq(StringUtils.isNotNull(logDTO.getState()),Log::getState, logDTO.getState());
            if (StringUtils.isNotNull(logDTO.getLogTimeStart()) && StringUtils.isNotNull(logDTO.getLogTimeEnd())) {
                wrapper.gt(Log::getCreateTime, logDTO.getLogTimeStart()).and(a -> a.lt(Log::getCreateTime, logDTO.getLogTimeEnd()));
            }
        }
        wrapper.orderByDesc(Log::getCreateTime);
        Page<Log> page = new Page<>(current, pageSize);
        logMapper.selectPage(page,wrapper);
        List<LogVO> logVOS = page.getRecords().stream().map(log -> log.asViewObject(LogVO.class, v -> v.setLoginTime(log.getCreateTime()))).toList();

        return PageVO.builder().page(logVOS).total(page.getTotal()).build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult<Void> deleteLog(LogDeleteDTO logDeleteDTO) {
        if (this.removeByIds(logDeleteDTO.getIds())) {
            return ResponseResult.success();
        }
        return ResponseResult.failure();
    }
}
