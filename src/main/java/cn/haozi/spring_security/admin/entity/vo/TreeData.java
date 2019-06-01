package cn.haozi.spring_security.admin.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/11 23:04
 * @Description:
 */
@Data
public class TreeData<T> implements Serializable {

    private Long count;

    private List<T> data;

    private  String msg;

    private Integer  code;

}
