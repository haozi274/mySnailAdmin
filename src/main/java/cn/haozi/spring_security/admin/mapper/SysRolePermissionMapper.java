package cn.haozi.spring_security.admin.mapper;

import cn.haozi.spring_security.admin.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/14 17:41
 * @Description: 角色与权限
 */

@Repository
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    void insertList(Integer [] arr);

    void saveRolePermission(SysRolePermission sysRolePermission);
}
