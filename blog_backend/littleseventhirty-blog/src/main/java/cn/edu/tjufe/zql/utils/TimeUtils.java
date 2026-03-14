package cn.edu.tjufe.zql.utils;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @authro 钟奇林
 * @description 自定义时间工具类2
 * @date 2026/3/14
 * @github https://github.com/little-seven-thirty
 */
public class TimeUtils {

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtils.YYYY_MM_DD_HH_MM_SS);

    public static String getCurrentTime() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }


    /**
     * 格式化时间
     * @param date 时间
     * @param format 格式
     * @return 格式化后的时间
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
