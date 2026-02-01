package cn.edu.tjufe.zql.utils;

import cn.edu.tjufe.zql.domain.response.ResponseResult;

import java.util.function.Supplier;

/**
 * @author: littleseventhirty
 * @description: 统一响应封装+异常处理流程控制 原本的ControllerUtils太泛了  ，工具类不应被实例化
 * @date: 2025/12/31-16:19
 **/
public final class ResponseWrapper {
    private ResponseWrapper() {
    }

    public static <T> ResponseResult<T> handler(Supplier<T> supplier) {
        try {
            return ResponseResult.<T>success(supplier.get());
        } catch (Exception e) {
            // 此处异常处理逻辑计划是专门开一个文件填写错误日志用的
            return ResponseResult.<T>failure(e.getMessage());
        }
    }
}
