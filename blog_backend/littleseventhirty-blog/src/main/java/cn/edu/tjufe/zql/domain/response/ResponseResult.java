package cn.edu.tjufe.zql.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author: littleseventhirty
 * @description: 统一响应类
 * @date: 2025/12/22-16:32
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // 控制转为json时不包含的字段
public class ResponseResult<T> implements Serializable {
    private String code;

    private String msg;

    private T data;
}
