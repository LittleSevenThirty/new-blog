package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.StationmasterInfoDTO;
import cn.edu.tjufe.zql.domain.dto.WebsiteInfoDTO;
import cn.edu.tjufe.zql.domain.entity.WebsiteInfo;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.WebsiteInfoVO;
import cn.edu.tjufe.zql.enums.UploadEnum;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/8-17:24
 **/
public interface IWebsiteInfoService extends IService<WebsiteInfo> {
    /**
     * 查询网站信息
     * @return 网站信息
     */
    WebsiteInfoVO getWebsiteInfo();

    /**
     * 上传或更新图片
     *
     * @param uploadEnum 目录
     * @param avatar     图片
     * @param type       类型
     * @return 是否成功&图片地址
     */
    ResponseResult<String> uploadImageInsertOrUpdate(UploadEnum uploadEnum, MultipartFile avatar, Integer type);

    /**
     * 修改站长信息
     * @param stationmasterInfoDTO 站长信息
     * @return 是否成功
     */
    ResponseResult<Void> updateStationmasterInfo(StationmasterInfoDTO stationmasterInfoDTO);

    /**
     * 修改网站信息
     * @param websiteInfoDTO 网站信息
     * @return 是否成功
     */
    ResponseResult<Void> updateWebsiteInfo(WebsiteInfoDTO websiteInfoDTO);
}
