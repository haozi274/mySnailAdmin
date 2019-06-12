package cn.haozi.spring_security.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 防水墙配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "tcaptcha")
public class TCaptchaVerifyConfig {

    private String app_id;

    private String app_secret;

    private String  verify_uri;
}
