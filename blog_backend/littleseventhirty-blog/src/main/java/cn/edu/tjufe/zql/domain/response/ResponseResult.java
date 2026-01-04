package cn.edu.tjufe.zql.domain.response;

import cn.edu.tjufe.zql.enums.ResponseEnum;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.coyote.Response;

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
    @Schema(description = "状态码")
    private String code;

    @Schema(description = "返回信息")
    private String msg;

    @Schema(description = "返回数据")
    private T data;

    /**
     * 成功响应，不需要返回数据
     * @return
     * @param <T>
     */
    public static<T> ResponseResult<T> success(){
        return new ResponseResult<T>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(),null);
    }

    /**
     * 成功响应，需要返回数据
     * @param data
     * @return
     * @param <T>
     */
    public static<T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功响应，返回自定义信息和数据
     * @param data
     * @param msg
     * @return
     * @param <T>
     */
    public static<T> ResponseResult<T> success(T data,String msg){
        return new ResponseResult<T>(ResponseEnum.SUCCESS.getCode(), msg,data);
    }

    /**
     * 失败响应，不需要返回数据
     * @param msg
     * @return
     * @param <T>
     */
    public static<T> ResponseResult<T> failure(String code,String msg){
        return new ResponseResult<T>(code,msg,null);
    }

    public static <T> ResponseResult<T> failure() {
        return new ResponseResult<>(ResponseEnum.FAILURE.getCode(), ResponseEnum.FAILURE.getMessage(), null);
    }
    public static <T> ResponseResult<T> failure(String msg) {
        return new ResponseResult<>(ResponseEnum.FAILURE.getCode(), msg, null);
    }

    public String asJsonString(){
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}
