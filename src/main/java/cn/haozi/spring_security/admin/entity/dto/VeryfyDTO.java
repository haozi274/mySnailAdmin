package cn.haozi.spring_security.admin.entity.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 陈思浩
 * @Date: 2019/5/22 21:10
 * @Description:
 */
@Data
public class VeryfyDTO implements Serializable{

    private String geetestChallenge;
    private String geetestValidate;
    private String geetestSeccode;

    private String userName;
    private String password;

}
