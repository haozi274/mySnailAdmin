package cn.haozi.spring_security.admin.mapper;

import cn.haozi.spring_security.admin.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/11 22:45
 * @Description:
 */
public interface SysMeunMapper extends BaseMapper<SysMenu> {

    void deleteByParentId(@Param(value = "id") Integer id);

    List<SysMenu> findMenuByRoleId(Integer roleId);

    List<SysMenu> findByUserId(@Param(value = "id") Integer id);

}
