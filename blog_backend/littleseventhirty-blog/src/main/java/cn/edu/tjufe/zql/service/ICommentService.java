package cn.edu.tjufe.zql.service;

import cn.edu.tjufe.zql.domain.dto.CommentDTO;
import cn.edu.tjufe.zql.domain.entity.Comment;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.CommentVO;
import cn.edu.tjufe.zql.domain.vo.PageVO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

public interface ICommentService extends IService<Comment> {
    ResponseResult<Void> addComment(CommentDTO commentDTO);
    ResponseResult<PageVO<List<CommentVO>>> getCommentList(Integer type, Long typeId, Integer pageNum, Integer pageSize);
}
