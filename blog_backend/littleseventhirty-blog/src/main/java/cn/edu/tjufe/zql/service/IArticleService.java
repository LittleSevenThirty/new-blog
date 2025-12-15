package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.entity.Article;
import cn.edu.tjufe.zql.domain.vo.SearchArticleByContentVO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2025/12/11-17:22
 **/
public interface IArticleService extends IService<Article> {
    /**
     * 根据搜索内容返回对应结果
     * @param content 被搜索目标
     * @return
     */
    List<SearchArticleByContentVO>  searchArticleByContent(String content);
}
