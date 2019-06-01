package cn.haozi.spring_security.admin.service;

import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.entity.dto.SysUserDTO;
import cn.haozi.spring_security.admin.entity.vo.UserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/6 10:44
 * @Description:
 */
public interface SysUserService extends IService<SysUser> {
    IPage<SysUser> selectPageVo(Page page, QueryWrapper<SysUser> wrapper);

    boolean saveUser(SysUserDTO sysUser);

    boolean removeUserById(Integer id);

    boolean updateUserById(SysUserDTO sysUserDTO);

    SysUser findById(Integer id);

    UserInfo  findUserInfo(Integer id);

    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * 注意!!: 如果入参是有多个,需要加注解指定参数名才能在xml中取值
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param state 状态
     * @return 分页对象
     */
   // IPage<SysUser> selectPageVo(Page page, @Param("status") Integer status);
    
}
