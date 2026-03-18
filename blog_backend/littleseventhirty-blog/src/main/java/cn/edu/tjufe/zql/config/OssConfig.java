package cn.edu.tjufe.zql.config;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @authro 钟奇林
 * @description
 * @date 2026/3/17
 * @github https://github.com/little-seven-thirty
 */
@Configuration
public class OssConfig {
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;
    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Bean(name = "ossClient")
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
