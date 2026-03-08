package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.CommentIsCheckDTO;
import cn.edu.tjufe.zql.domain.dto.SearchCommentDTO;
import cn.edu.tjufe.zql.domain.dto.UserCommentDTO;
import cn.edu.tjufe.zql.domain.entity.Comment;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.CommentListVO;
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

    /**
     * 后台评论列表
     * @return 结果
     */
    List<CommentListVO> getBackCommentList(SearchCommentDTO searchDTO);

    /**
     * 是否通过评论
     * @param isCheckDTO 是否通过
     * @return 是否成功
     */
    ResponseResult<Void> isCheckComment(CommentIsCheckDTO isCheckDTO);

    /**
     * 删除评论
     * @param id id 列表
     * @return 是否成功
     */
    ResponseResult<Void> deleteComment(Long id);
}
