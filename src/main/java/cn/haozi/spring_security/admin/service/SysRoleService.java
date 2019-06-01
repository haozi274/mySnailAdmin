package cn.haozi.spring_security.admin.service;

import cn.haozi.spring_security.admin.entity.SysRole;
import cn.haozi.spring_security.admin.entity.dto.SysRoleDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/14 15:28
 * @Description:
 */
public interface SysRoleService extends IService<SysRole> {

    boolean saveRole(SysRoleDTO sysRoleDTO);

    boolean deleteRole(Integer id);

    boolean updateRoleById(SysRoleDTO sysRole);

    List<SysRole> findByRoleId(int userId);
}
