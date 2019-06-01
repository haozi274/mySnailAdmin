package cn.haozi.spring_security.admin.entity.vo;

import cn.haozi.spring_security.admin.entity.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/14 19:54
 * @Description:
 */

public class TreeRolePermission extends SysMenu {

    private String status;
    private List<TreeRolePermission> list = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public List<TreeRolePermission> getList() {
        return list;
    }

    public void setList(List<TreeRolePermission> list) {
        this.list = list;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
