package cn.haozi.spring_security.admin.service.impl;

import cn.haozi.spring_security.admin.entity.SysRole;
import cn.haozi.spring_security.admin.entity.SysRolePermission;
import cn.haozi.spring_security.admin.entity.dto.SysRoleDTO;
import cn.haozi.spring_security.admin.mapper.SysRoleMapper;
import cn.haozi.spring_security.admin.mapper.SysRolePermissionMapper;
import cn.haozi.spring_security.admin.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoleilu.hutool.bean.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/14 15:29
 * @Description:
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    /***
     * 添加角色和权限
     * @param sysRole
     * @return
     */
    @Override
    public boolean saveRole(SysRoleDTO sysRole) {

        /**
         * 添加角色的权限
         */
        SysRole role = new SysRole();
        BeanUtil.copyProperties(sysRole,role);
        this.baseMapper.saveRole(role);
        sysRole.setId(role.getId());
          if(sysRole.getPermissionList().length>0){
            for(int i =0;i<sysRole.getPermissionList().length;i++){
                SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleId(role.getId());
                sysRolePermission.setPerId(sysRole.getPermissionList()[i]);
                sysRolePermissionMapper.saveRolePermission(sysRolePermission);
            }
        }
        return role.getId()>0?true:false;
    }

    /***
     * 删除角色和权限
     * @param id
     * @return
     */
    @Override
    public boolean deleteRole(Integer id) {
        boolean flag = this.removeById(id);
        if(flag){
            QueryWrapper<SysRolePermission> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",id);
            List<SysRolePermission> list = sysRolePermissionMapper.selectList(wrapper);
           for (SysRolePermission permission:list) {
               sysRolePermissionMapper.deleteById(permission.getPerId());
           }
        }
        return flag;
    }

    /**
     * 修改权限、角色信息
     */
    @Override
    public boolean updateRoleById(SysRoleDTO sysRoleDTO) {

        /**
         * 根据角色查询所有的权限
         */
            QueryWrapper<SysRolePermission> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",sysRoleDTO.getId());
            List<SysRolePermission> sysRolePermissionList =   sysRolePermissionMapper.selectList(wrapper);

                for (int j =0;j<sysRoleDTO.getPermissionList().length;j++) {
                    for(int i = 0; i<sysRolePermissionList.size();i++){
                        if (sysRolePermissionList.get(i).getPerId() == sysRoleDTO.getPermissionList()[j]) {
                            sysRolePermissionList.remove(i);
                            sysRoleDTO.getPermissionList()[j] =0;
                            i--;
                        }
                }
            }
            //移除的权限
           sysRolePermissionList.stream().forEach(v->sysRolePermissionMapper.deleteById(v.getId()));
            //新增权限
            for (int i = 0; i<sysRoleDTO.getPermissionList().length;i++){
                if (sysRoleDTO.getPermissionList()[i] != 0 ){
                    SysRolePermission sysRolePermission = new SysRolePermission();
                    sysRolePermission.setPerId(sysRoleDTO.getPermissionList()[i]);
                    sysRolePermission.setRoleId(sysRoleDTO.getId());
                    sysRolePermissionMapper.saveRolePermission(sysRolePermission);
                }
            }
            SysRole sysRole = new SysRole();
            BeanUtils.copyProperties(sysRoleDTO,sysRole);
        return this.updateById(sysRole)?true:false;
    }

    @Override
    public List<SysRole> findByRoleId(int userId) {
        return this.baseMapper.findByRoleId(userId);
    }
}
