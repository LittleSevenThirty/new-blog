package cn.edu.tjufe.zql.utils;

/**
 * @author: littleseventhirty
 * @description: 字符串工具类
 * @date: 2026/1/9-10:47
 **/
public final class StringUtils {
    private StringUtils() {
    }

    /**
     * 去除字符串中markdown格式
     *
     * @param markdown
     * @return 纯文本字符串
     */
    public static String stripMarkdown(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return markdown;
        }
        return markdown.replaceAll("(?m)^#+\\s+", "")   // 去除标题
                .replaceAll("(?s)```.*?```", "")    // 去除代码块（多行）
                .replaceAll("`[^`]*`", "")  // 去掉行内代码
                .replaceAll("!?\\[(.*?)\\]\\(.*?\\)", "$1") // 去掉链接和图片
                .replaceAll("(\\*\\*|__)(.*?)\\1", "$2")    //去除粗体和斜体
                .replaceAll("(\\*|_)(.*?)\\1", "$2")
                .replaceAll("(?m)^[\\s]*[-*+]\\s+", "") // 去除无序列表标识
                .replaceAll("(?m)^[\\s]*\\d+\\.\\s+", "")    // 去除有序列表标识
                .replaceAll("(?m)^\\s*>\\s*", "")    // 去除引用
                .replaceAll("(?m)^[-*_]{3,}\\s*$", "");   // 去除水平线
        // 去除HTML标签（如果需要）replaceAll("<[^>]*>", "");
    }
}
