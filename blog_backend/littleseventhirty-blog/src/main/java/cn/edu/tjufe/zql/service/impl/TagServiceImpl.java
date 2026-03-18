package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.constants.FunctionConst;
import cn.edu.tjufe.zql.domain.dto.SearchTagDTO;
import cn.edu.tjufe.zql.domain.dto.TagDTO;
import cn.edu.tjufe.zql.domain.entity.ArticleTag;
import cn.edu.tjufe.zql.domain.entity.Tag;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.TagVO;
import cn.edu.tjufe.zql.mapper.ArticleTagMapper;
import cn.edu.tjufe.zql.mapper.TagMapper;
import cn.edu.tjufe.zql.service.ITagService;
import cn.edu.tjufe.zql.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/28-16:52
 **/
@Service(value = "tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public ResponseResult<Void> addTag(TagDTO tagDTO) {
        if (this.save(tagDTO.asViewObject(Tag.class))) return ResponseResult.success();
        return ResponseResult.failure();
    }

    @Override
    public List<TagVO> listAllTag() {
        return this.query().list().stream().map(tag->
                tag.asViewObject(TagVO.class,
                        item->item.setArticleCount(
                                articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getTagId,tag.getTagId()))
                        )
                )
        ).toList();
    }

    @Override
    public List<TagVO> searchTag(SearchTagDTO searchTagDTO) {
        // 构造条件查询器
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        // tagName存在就搜索tagName
        queryWrapper.like(StringUtils.isNotEmpty(searchTagDTO.getTagName()), Tag::getTagName, searchTagDTO.getTagName());
        // 存在时间区间就限制搜索时间段
        if (StringUtils.isNotNull(searchTagDTO.getStartTime()) && StringUtils.isNotNull(searchTagDTO.getEndTime()))
            queryWrapper.between(Tag::getCreateTime, searchTagDTO.getStartTime(), searchTagDTO.getEndTime());

        return tagMapper.selectList(queryWrapper)
                .stream()
                .map(tag ->
                        tag.asViewObject(TagVO.class, item ->
                                item.setArticleCount(articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
                                        .eq(ArticleTag::getTagId, tag.getTagId())))))
                .toList();
    }

    @Override
    public TagVO getTagById(Long id) {
        return tagMapper.selectById(id).asViewObject(TagVO.class);
    }

    // 涉及更新数据库操作使用回滚相关注解
    @Transactional
    @Override
    public ResponseResult<Void> addOrUpdateTag(TagDTO tagDTO) {
        if (this.saveOrUpdate(tagDTO.asViewObject(Tag.class))) return ResponseResult.success();
        return ResponseResult.failure();
    }

    @Transactional
    @Override
    public ResponseResult<Void> deleteTagByIds(List<Long> ids) {
        // 是否有剩下文章
        Long count = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>().in(ArticleTag::getTagId, ids));
        if (count > 0) return ResponseResult.failure(FunctionConst.TAG_EXIST_ARTICLE);
        // 执行删除
        if (this.removeByIds(ids)) return ResponseResult.success();
        return ResponseResult.failure();
    }
}
