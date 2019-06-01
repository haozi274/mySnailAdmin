package cn.haozi.spring_security.admin.entity.dto;

import cn.haozi.spring_security.admin.entity.SysDictionary;
import lombok.Data;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/16 21:44
 * @Description:
 */
@Data
public class SysDictionaryDTO extends SysDictionary {

    private int page =1;

    private int limit = 10;
}
