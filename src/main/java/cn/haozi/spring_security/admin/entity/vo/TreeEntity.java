package cn.haozi.spring_security.admin.entity.vo;

import cn.haozi.spring_security.admin.entity.SysMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/12 15:37
 * @Description: 菜单
 */
@Data
public class TreeEntity extends SysMenu implements Serializable {

    private List<TreeEntity> list;
}
