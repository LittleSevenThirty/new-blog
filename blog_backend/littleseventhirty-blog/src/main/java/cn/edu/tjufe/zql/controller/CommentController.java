package cn.edu.tjufe.zql.controller;

import cn.edu.tjufe.zql.annotation.AccessIntercepter;
import cn.edu.tjufe.zql.annotation.CheckBlacklist;
import cn.edu.tjufe.zql.annotation.LogAnnotation;
import cn.edu.tjufe.zql.constants.LogConst;
import cn.edu.tjufe.zql.domain.dto.CommentIsCheckDTO;
import cn.edu.tjufe.zql.domain.dto.SearchCommentDTO;
import cn.edu.tjufe.zql.domain.dto.UserCommentDTO;
import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.domain.vo.CommentListVO;
import cn.edu.tjufe.zql.domain.vo.CommentVO;
import cn.edu.tjufe.zql.domain.vo.PageVO;
import cn.edu.tjufe.zql.service.ICommentService;
import cn.edu.tjufe.zql.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name = "评论相关接口")
@RequestMapping("/comment")
@Validated
public class CommentController {

    @Resource
    private ICommentService commentService;

    @CheckBlacklist
    @Operation(summary = "用户添加评论")
    @Parameter(name = "commentDTO", description = "评论信息", required = true)
    @AccessIntercepter(seconds = 60, maxCount = 10)
    @PostMapping("/auth/add/comment")
    public ResponseResult<String> userComment(@Valid @RequestBody UserCommentDTO commentDTO) {
        return commentService.userComment(commentDTO);
    }

    @GetMapping("/getComment")
    @Parameters({
            @Parameter(name = "type", description = "评论类型", required = true),
            @Parameter(name = "typeId", description = "评论id", required = true),
            @Parameter(name = "pageNum", description = "页码", required = true),
            @Parameter(name = "pageSize", description = "每页数量", required = true)
    })
    @AccessIntercepter(seconds = 60, maxCount = 60)
    @Operation(summary = "获取评论列表")
    public ResponseResult<PageVO<List<CommentVO>>> getCommentList(
            @RequestParam Integer type,
            @RequestParam Long typeId,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize) {
        return ResponseWrapper.handler((() -> commentService.getCommentList(type, typeId, pageNum, pageSize)));
    }

    @PreAuthorize("hasAnyAuthority('blog:comment:list')")
    @Operation(summary = "后台评论列表")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module="评论管理",operation= LogConst.GET)
    @GetMapping("/back/list")
    public ResponseResult<List<CommentListVO>> backList() {
        return ResponseWrapper.handler(() -> commentService.getBackCommentList(null));
    }

    @PreAuthorize("hasAnyAuthority('blog:comment:search')")
    @Operation(summary = "搜索后台评论列表")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module="评论管理",operation= LogConst.SEARCH)
    @PostMapping("/back/search")
    public ResponseResult<List<CommentListVO>> backList(@RequestBody SearchCommentDTO searchDTO) {
        return ResponseWrapper.handler(() -> commentService.getBackCommentList(searchDTO));
    }

    @PreAuthorize("hasAnyAuthority('blog:comment:isCheck')")
    @Operation(summary = "修改评论是否通过")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module="评论管理",operation= LogConst.UPDATE)
    @PostMapping("/back/isCheck")
    public ResponseResult<Void> isCheck(@RequestBody @Valid CommentIsCheckDTO commentIsCheckDTO) {
        return commentService.isCheckComment(commentIsCheckDTO);
    }

    @PreAuthorize("hasAnyAuthority('blog:comment:delete')")
    @Operation(summary = "删除评论")
    @AccessIntercepter(seconds = 60, maxCount = 30)
    @LogAnnotation(module="评论管理",operation= LogConst.DELETE)
    @DeleteMapping("/back/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable("id") Long id) {
        return commentService.deleteComment(id);
    }

}
