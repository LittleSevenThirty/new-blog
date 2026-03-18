package cn.edu.tjufe.zql.utils;


import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @authro 钟奇林
 * @description Web工具类
 * @date 2026/3/4
 * @github https://github.com/little-seven-thirty
 */
public class WebUtil {

    /**
     * 将字符串渲染到客户端
     * <p>
     * 该方法用于将指定的字符串作为响应内容发送给客户端，通常用于返回JSON格式的数据
     * 设置响应状态码为200，表示请求成功
     * 设置内容类型为application/json，确保客户端正确解析响应
     * 设置字符编码为utf-8，防止中文乱码
     * <p>
     * @param response HTTP响应对象，用于设置响应属性和获取输出流
     * @param string 待渲染的字符串，通常为JSON格式
     * @return null，该方法无实际返回值
     */
    public static String renderString(HttpServletResponse response, String string) {
        try
        {
            // 设置响应状态码为200，表示请求成功
            response.setStatus(200);
            // 设置响应内容类型为application/json，确保客户端正确解析
            response.setContentType("application/json");
            // 设置字符编码为utf-8，防止中文乱码
            response.setCharacterEncoding("utf-8");
            // 获取PrintWriter对象，用于向客户端输出内容
            PrintWriter writer = response.getWriter();
            // 输出字符串内容
            writer.println(string);
            // 刷新输出流，确保内容被立即发送
            writer.flush();
            // 关闭输出流，释放资源
            writer.close();
        }
        catch (IOException e)
        {
            // 捕获并打印IO异常，确保方法不会因为异常而中断
            e.printStackTrace();
        }
        // 返回null，该方法无实际返回值
        return null;
    }
}
