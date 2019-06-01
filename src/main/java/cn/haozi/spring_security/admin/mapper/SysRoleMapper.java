package cn.haozi.spring_security.admin.mapper;

import cn.haozi.spring_security.admin.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/14 15:30
 * @Description:
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    @Insert("insert sys_role (name,remark) values (#{role.name},#{role.remark})")
    void  saveRole(@Param("role") SysRole role);

    List<SysRole> findByRoleId(int userId);
}
