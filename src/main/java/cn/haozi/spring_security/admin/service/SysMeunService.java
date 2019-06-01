package cn.haozi.spring_security.admin.service;

import cn.haozi.spring_security.admin.entity.SysMenu;
import cn.haozi.spring_security.admin.entity.vo.TreeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/11 22:43
 * @Description:
 */
public interface SysMeunService extends IService<SysMenu> {
    List<TreeEntity> findAllList(boolean flag);

    SysMenu findById(Integer id);

    boolean insert(SysMenu sysMenu);

    boolean deleteById(Integer id);

    boolean deleteByParentId(Integer id);

    List<SysMenu> findMenuByRoleId(Integer roleId);
     List<TreeEntity> findByUserId(Integer userId) ;

}
