package cn.haozi.spring_security.admin.entity.dto;

import cn.haozi.spring_security.admin.entity.SysFile;
import lombok.Data;

@Data
public class SysFileDTO extends SysFile {

    private int page =1;

    private int limit = 10;
}
