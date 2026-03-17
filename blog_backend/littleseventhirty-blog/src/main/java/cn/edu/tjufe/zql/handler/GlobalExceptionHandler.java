package cn.edu.tjufe.zql.handler;


import cn.edu.tjufe.zql.domain.response.ResponseResult;
import cn.edu.tjufe.zql.enums.ResponseEnum;
import cn.edu.tjufe.zql.exception.BlackListException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 *
 * @authro 钟奇林
 * @description 适用@RestControllerAdvice定义为全局异常处理器
 * @date 2026/3/15
 * @github https://github.com/little-seven-thirty
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 统一处理参数校验异常
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<Void> handlerConstraintViolationException(ConstraintViolationException e){
        log.error("参数校验异常:{}({})", e.getMessage(), e.getStackTrace());
        return ResponseResult.failure(ResponseEnum.PARAM_ERROR.getCode(), e.getMessage().split(":")[1]);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Void> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("参数校验异常:{}({})", e.getMessage(), e.getStackTrace());
        BindingResult bindingResult = e.getBindingResult();
        return ResponseResult.failure(ResponseEnum.PARAM_ERROR.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseResult<Void> handlerFileUploadException(FileUploadException e){
        log.error("文件上传异常:{}({})", e.getMessage(), e.getStackTrace());
        String bindingResult = e.getMessage();
        return ResponseResult.failure(ResponseEnum.FILE_UPLOAD_ERROR.getCode(), bindingResult);
    }

    @ExceptionHandler(BlackListException.class)
    public ResponseResult<Void> handlerBlackListException(BlackListException e){
        log.error("黑名单异常:{}({})", e.getMessage(), e.getStackTrace());
        return ResponseResult.failure(ResponseEnum.BLACK_LIST_ERROR.getCode(), e.getMessage());
    }

    /**
     * 兜底的异常处理器（最大的异常处理器，任何未包含的异常处理都会被这个方法拦截
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handlerException(Exception e){
        log.error("系统异常:{}，异常:{}",e.getMessage(),e.getStackTrace());
        return ResponseResult.failure(ResponseEnum.OTHER_ERROR.getCode(), e.getMessage());
    }
}
