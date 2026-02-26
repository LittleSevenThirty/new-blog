package cn.edu.tjufe.zql.domain.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class CommentVO {
    private Long id;
    private Long userId;
    private String userAvatar;
    private String userNickname;
    private String content;
    private Date createTime;
    private Long parentId;
    private Long replyId;
    private String replyUserNickname;
    private List<CommentVO> childComment;
}
