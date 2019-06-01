package cn.haozi.spring_security.admin.entity.dto;

import cn.haozi.spring_security.admin.entity.SysMenu;
import lombok.Data;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/13 17:50
 * @Description:
 */
@Data
public class SysMeunDTO extends SysMenu {

    private String parentName;
}
