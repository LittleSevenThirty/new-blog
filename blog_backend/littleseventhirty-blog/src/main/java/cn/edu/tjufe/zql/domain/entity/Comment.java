package cn.edu.tjufe.zql.domain.entity;

import cn.edu.tjufe.zql.domain.ViewObjectConvertible;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: littleseventhirty
 * @description:
 * @date: 2026/1/9-16:42
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_comment")
public class Comment implements ViewObjectConvertible {
}
