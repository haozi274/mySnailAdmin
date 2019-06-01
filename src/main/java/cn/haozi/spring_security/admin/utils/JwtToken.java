package cn.haozi.spring_security.admin.utils;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/22 20:57
 * @Description:
 */
@Data
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}