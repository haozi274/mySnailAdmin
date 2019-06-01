package cn.haozi.spring_security.admin.utils;

import lombok.Data;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/7 15:23
 * @Description:
 */
@Data
public class LayuiUtils<T> {
    private Integer code = 0;
    private Long count;
    private List<T> data;
    private String msg = "";
    private String  parentId ;


}
