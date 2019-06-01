package cn.haozi.spring_security.admin.entity.dto;

import cn.haozi.spring_security.admin.entity.Log;
import lombok.Data;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 13:26
 * @Description:
 */
@Data
public class SysLogDTO extends Log {
    private Integer page;

    private Integer limit;
}
