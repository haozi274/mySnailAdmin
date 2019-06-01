package cn.haozi.spring_security.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "qiniu")
public class ConstantQiniu {

    private  String accessKey;

    private  String secretKey;

    private  String bucketName;

    private  String path;
}
