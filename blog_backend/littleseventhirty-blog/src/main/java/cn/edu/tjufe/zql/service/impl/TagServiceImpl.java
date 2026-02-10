package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.domain.entity.ArticleTag;
import cn.edu.tjufe.zql.domain.entity.Tag;
import cn.edu.tjufe.zql.domain.vo.TagVO;
import cn.edu.tjufe.zql.mapper.ArticleTagMapper;
import cn.edu.tjufe.zql.mapper.TagMapper;
import cn.edu.tjufe.zql.service.ITagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/28-16:52
 **/
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

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
}
