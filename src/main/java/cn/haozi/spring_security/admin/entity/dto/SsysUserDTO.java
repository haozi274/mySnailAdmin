package cn.haozi.spring_security.admin.entity.dto;

import cn.haozi.spring_security.admin.entity.SysUser;
import lombok.Data;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/9 13:19
 * @Description:
 */
@Data
public class SsysUserDTO extends SysUser {

    /**
     * 頁數
     */
    private Integer page = 1;
    /**
     * 每頁顯示數量+
     */
    private Integer limit =10;
}
