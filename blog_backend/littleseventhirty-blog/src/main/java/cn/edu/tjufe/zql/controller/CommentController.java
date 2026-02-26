package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.domain.dto.CommentDTO;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.CommentVO;
import cn.edu.tjufe.zql.domain.vo.PageVO;
import cn.edu.tjufe.zql.service.ICommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Tag(name = "评论管理")
public class CommentController {

    @Resource
    private ICommentService commentService;

    @PostMapping("/auth/add/comment")
    @Operation(summary = "添加评论")
    public ResponseResult<Void> addComment(@RequestBody CommentDTO commentDTO) {
        return commentService.addComment(commentDTO);
    }

    @GetMapping("/getComment")
    @Operation(summary = "获取评论列表")
    public ResponseResult<PageVO<List<CommentVO>>> getCommentList(
            @RequestParam Integer type,
            @RequestParam Long typeId,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize) {
        return commentService.getCommentList(type, typeId, pageNum, pageSize);
    }
}
