package cn.edu.tjufe.zql.service.impl;

import cn.edu.tjufe.zql.domain.entity.Comment;
import cn.edu.tjufe.zql.mapper.CommentMapper;
import cn.edu.tjufe.zql.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/9-16:46
 **/
@Slf4j
@Service(value = "commentService")
public class CommentService extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
}
