package cn.edu.tjufe.zql.exception;

public class FileUploadException extends Exception {
    public FileUploadException(String message) {
        super(message);
    }
    public FileUploadException() { super("文件上传异常");}
}
