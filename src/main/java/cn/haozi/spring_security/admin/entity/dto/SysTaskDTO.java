package cn.haozi.spring_security.admin.entity.dto;

import cn.haozi.spring_security.admin.entity.SysTask;
import lombok.Data;

@Data
public class SysTaskDTO extends SysTask {
    private int page =1;

    private int limit = 10;

}
