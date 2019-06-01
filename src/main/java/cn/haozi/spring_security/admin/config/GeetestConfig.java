package cn.haozi.spring_security.admin.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * GeetestWeb配置文件
 * 
 *
 */
@Component
@ConfigurationProperties(prefix = "geetest")
@Data
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	private String geetestId ;
	private  String geetestKey;
	private boolean newfailback ;



}
