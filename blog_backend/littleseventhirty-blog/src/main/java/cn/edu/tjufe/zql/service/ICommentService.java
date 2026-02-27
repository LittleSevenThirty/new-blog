package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.UserCommentDTO;
import cn.edu.tjufe.zql.domain.entity.Comment;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.CommentVO;
import cn.edu.tjufe.zql.domain.vo.PageVO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

public interface ICommentService extends IService<Comment> {


    /**
     * 添加评论
     */
    ResponseResult<String> userComment(UserCommentDTO commentDTO);

    /**
     * 查询文章评论
     * @param type  评论类型
     * @param typeId 对应类型id
     * @param pageNum 页数
     * @param pageSize 页面容量
     * @return
     */
    PageVO<List<CommentVO>> getCommentList(Integer type, Long typeId, Integer pageNum, Integer pageSize);
}
