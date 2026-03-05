package cn.edu.tjufe.zql.utils;


import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @authro 钟奇林
 * @description 浏览器工具类
 * @date 2026/3/3
 * @github https://github.com/little-seven-thirty
 */
public class BrowserUtil {
    /**
     * 获取浏览器名称及版本
     * @param request request
     * @return 名称 - 版本号
     */
    public static String browserName(HttpServletRequest request){
        // 获取HTTP标头
        String userAgent = request.getHeader("User-Agent");
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        Browser browser = ua.getBrowser();
        return browser.getName() + "-" + browser.getVersion(userAgent);
    }

    /**
     * 获取操作系统名称
     * @param request request
     * @return 名称
     */
    public static String osName(HttpServletRequest request){
        String userAgent = request.getHeader("User-Agent");
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        OperatingSystem os = ua.getOperatingSystem();
        return os.getName();
    }
}
