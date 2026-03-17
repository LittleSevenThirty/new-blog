package cn.edu.tjufe.zql.utils;


import cn.edu.tjufe.zql.enums.UploadEnum;
import cn.edu.tjufe.zql.exception.FileUploadException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @authro 钟奇林
 * @description 文件上传工具类-这里后期调整，哪家便宜用哪家
 * @date 2026/3/7
 * @github https://github.com/little-seven-thirty
 */
@Slf4j
@Component
public class FileUploadUtils {

    @Resource
    private OSS client;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    @Value("${aliyun.oss.urlPrefix}")
    private String urlPrefix;

    /**
     * 上传文件
     *
     * @param uploadEnum 文件枚举
     * @param file       文件
     * @return 上传后的文件地址形如 https://little-seven.oss-cn-beijing.aliyuncs.com/article/articleCover/uuidname.png
     * @throws Exception 异常
     */
    public String upload(UploadEnum uploadEnum, MultipartFile file) throws Exception {
        isCheck(uploadEnum, file);
        if (isFormatFile(file.getOriginalFilename(), uploadEnum.getFormat())) {
            String name = UUID.randomUUID().toString();
            String extension = getFileExtension(file.getOriginalFilename());
            String objectName = normalizeDir(uploadEnum.getDir()) + name + "." + extension;

            try (InputStream stream = file.getInputStream()) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(Objects.requireNonNull(file.getContentType()));
                metadata.setContentLength(file.getSize());

                client.putObject(bucketName, objectName, stream, metadata);
            }

            return urlPrefix + "/" + objectName;
        }
        log.error("--------------------上传文件格式不正确--------------------");
        throw new FileUploadException("上传文件类型错误");
    }

    /**
     * 上传文件 -- 指定文件名
     *
     * @param uploadEnum 文件枚举
     * @param file       文件
     * @param fileName   文件名 (不带后缀)
     * @return 上传后的文件地址
     */
    public String upload(UploadEnum uploadEnum, MultipartFile file, String fileName) throws Exception {
        isCheck(uploadEnum, file);
        if (isFormatFile(file.getOriginalFilename(), uploadEnum.getFormat())) {
            String extension = getFileExtension(file.getOriginalFilename());
            String objectName = normalizeDir(uploadEnum.getDir()) + fileName + "." + extension;

            try (InputStream stream = file.getInputStream()) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(Objects.requireNonNull(file.getContentType()));
                metadata.setContentLength(file.getSize());

                client.putObject(bucketName, objectName, stream, metadata);
            }

            return urlPrefix + "/" + objectName;
        }
        log.error("--------------------上传文件格式不正确--------------------");
        throw new FileUploadException("上传文件类型错误");
    }

    /**
     * 上传文件 -- 指定动态存储文件夹 -- 指定文件名
     *
     * @param uploadEnum 文件枚举
     * @param file       文件
     * @param fileName   文件名 (不带后缀)
     * @param dir        动态目录
     * @return 上传后的文件地址
     */
    public String upload(UploadEnum uploadEnum, MultipartFile file, String fileName, String dir) throws Exception {
        isCheck(uploadEnum, file);
        if (isFormatFile(file.getOriginalFilename(), uploadEnum.getFormat())) {
            String extension = getFileExtension(file.getOriginalFilename());
            String objectName = normalizeDir(uploadEnum.getDir()) + normalizeDir(dir) + fileName + "." + extension;

            try (InputStream stream = file.getInputStream()) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(Objects.requireNonNull(file.getContentType()));
                metadata.setContentLength(file.getSize());

                client.putObject(bucketName, objectName, stream, metadata);
            }

            return urlPrefix + "/" + objectName;
        }
        log.error("--------------------上传文件格式不正确--------------------");
        throw new FileUploadException("上传文件类型错误");
    }

    /**
     * 文件上传合法校验
     *
     * @param uploadEnum 文件枚举
     * @param file       文件
     * @throws FileUploadException 文件上传异常
     */
    public void isCheck(UploadEnum uploadEnum, MultipartFile file) throws FileUploadException {
        if (file == null || file.isEmpty()) {
            throw new FileUploadException("上传文件为空");
        }
        if (verifyTheFileSize(file.getSize(), uploadEnum.getLimitSize())) {
            throw new FileUploadException("上传文件超过限制大小:" + uploadEnum.getLimitSize() + "MB");
        }
    }

    /**
     * 获取文件后缀
     *
     * @param originalFilename 文件名
     * @return 文件后缀
     */
    public String getFileExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            return "";
        }
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    /**
     * 比较文件标准尺寸
     * @param fileSize
     * @param limitSize
     * @return
     */
    public Boolean verifyTheFileSize(Long fileSize, Double limitSize) {
        double formatFileSize = convertFileSizeToMB(fileSize);
        return formatFileSize >= limitSize;
    }

    /**
     * B 转 MB
     *
     * @param sizeInBytes 文件大小 B
     * @return 文件大小 MB
     */
    public double convertFileSizeToMB(long sizeInBytes) {
        double sizeInMB = (double) sizeInBytes / (1024 * 1024);
        String formatted = String.format("%.2f", sizeInMB);
        return Double.parseDouble(formatted);
    }

    /**
     * 获取目录下的所有文件名称
     *
     * @param dir 目录 eg:websiteInfo/avatar/
     * @return 所有文件全路径名称
     */
    public List<String> listFiles(String dir) {
        String prefix = normalizeDir(dir);
        List<String> fileNames = new ArrayList<>();

        try {
            String continuationToken = null;
            do {
                ListObjectsV2Request request = new ListObjectsV2Request(bucketName)
                        .withPrefix(prefix)
                        .withContinuationToken(continuationToken);

                ListObjectsV2Result result = client.listObjectsV2(request);
                for (OSSObjectSummary objectSummary : result.getObjectSummaries()) {
                    fileNames.add(objectSummary.getKey());
                }
                continuationToken = result.getNextContinuationToken();
            } while (continuationToken != null);
        } catch (Exception e) {
            log.error("获取文件出现错误", e);
        }

        return fileNames;
    }

    /**
     * 批量删除
     *
     * @param fileNames 文件名称(这里应传 objectName 列表，如 avatar/a.jpg)
     * @return 是否成功
     */
    public boolean deleteFiles(List<String> fileNames) {
        if (fileNames == null || fileNames.isEmpty()) {
            return true;
        }

        try {
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName);
            request.setKeys(fileNames);
            client.deleteObjects(request);
            return true;
        } catch (Exception e) {
            log.error("批量删除文件失败", e);
            return false;
        }
    }

    /**
     * 单文件删除
     *
     * @param fileName 文件名称
     * @param dir      文件目录
     * @return 是否成功, 成功：true, 失败：false
     */
    public boolean deleteFile(String dir, String fileName) {
        try {
            String objectName = normalizeDir(dir) + fileName;
            if (!isFileExist(dir, fileName)) {
                log.error("文件 {} 不存在", objectName);
                return false;
            }

            client.deleteObject(bucketName, objectName);
            log.info("文件 {} 已成功从 OSS 中删除", objectName);
            return true;
        } catch (Exception e) {
            log.error("删除 OSS 文件 {} 失败", fileName, e);
            return false;
        }
    }

    /**
     * 文件格式校验
     *
     * @param fileName 文件名称
     * @param format   支持的后缀
     * @return 是否支持
     */
    public boolean isFormatFile(String fileName, List<String> format) {
        if (fileName == null || format == null || format.isEmpty()) {
            return false;
        }
        for (String s : format) {
            if (fileName.endsWith(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断文件是否存在
     *
     * @param dir      目录
     * @param fileName 文件名
     * @return 是否存在，存在：true，不存在：false
     */
    public boolean isFileExist(String dir, String fileName) {
        try {
            String objectName = normalizeDir(dir) + fileName;
            return client.doesObjectExist(bucketName, objectName);
        } catch (Exception e) {
            log.error("判断文件是否存在出现错误", e);
            return false;
        }
    }

    /**
     * 完整路径中截取文件名
     *
     * @param path 完整路径
     * @return 文件名
     */
    public String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    /**
     * 文件大小转换(kb)
     *
     * @param fileSize 文件大小
     * @return 文件大小(kb)
     */
    public Double convertFileSizeToKB(Long fileSize) {
        return fileSize / 1024.0;
    }

    /**
     * 规范目录，保证以 / 结尾，且不以 / 开头
     */
    private String normalizeDir(String dir) {
        if (dir == null || dir.isBlank()) {
            return "";
        }
        dir = dir.trim();
        if (dir.startsWith("/")) {
            dir = dir.substring(1);
        }
        if (!dir.endsWith("/")) {
            dir += "/";
        }
        return dir;
    }
}
