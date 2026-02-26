package cn.edu.tjufe.zql.domain.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer type;
    private Long typeId;
    private Long parentId;
    private Long replyId;
    private String commentContent;
}
