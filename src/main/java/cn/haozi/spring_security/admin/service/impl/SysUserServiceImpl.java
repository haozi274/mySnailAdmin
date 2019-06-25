package cn.haozi.spring_security.admin.service.impl;

import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.entity.SysUserRole;
import cn.haozi.spring_security.admin.entity.dto.SysUserDTO;
import cn.haozi.spring_security.admin.entity.vo.UserInfo;
import cn.haozi.spring_security.admin.mapper.SysUserMapper;
import cn.haozi.spring_security.admin.mapper.SysUserRoleMapper;
import cn.haozi.spring_security.admin.service.SysUserService;
import cn.haozi.spring_security.admin.utils.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoleilu.hutool.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/6 10:45
 * @Description:
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /***
     * unless 表示条件表达式成立的话不放入缓存
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public IPage<SysUser> selectPageVo(Page page, QueryWrapper<SysUser> wrapper) {
       return this.page(page,wrapper);
    }

    /**
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     * @return
     */
  //  @CachePut(value = "user", key = "#root.targetClass +#sysUser", unless = "#sysUser eq null")
    @Override
    public boolean saveUser(SysUserDTO sysUser) {

        SysUser user = new SysUser();
        BeanUtil.copyProperties(sysUser,user);
        /**
         * 密码加密 对密码进行加密加盐
         */
        ToolUtil.entryptPassword(user);

       boolean flag =  this.save(user);
        /***
         * 添加角色
         *
         */
        for (Integer role:sysUser.getRoleList()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(role);
            sysUserRole.setUserId(user.getId());
            sysUserRoleMapper.insert(sysUserRole);
        }
         return flag ;
    }

    /**
     * @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
     * @return
     */
    @CacheEvict(value = "user", key = "#result.id", condition = "#result eq true")
    @Override
    public boolean removeUserById(Integer id) {
        System.out.println("缓存。。。。。。。。。。。。。。。。。。。。");
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        List<SysUserRole> list = sysUserRoleMapper.selectList(wrapper);
        for (SysUserRole sysUserRole:list  ) {
            sysUserRoleMapper.deleteById(sysUserRole.getId());
        }
        return this.baseMapper.deleteById(id)>0?true:false;
    }

    @Override
    public boolean updateUserById(SysUserDTO sysUserDTO) {
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserDTO,sysUser);
        /**
         * 查出用户和角色数据和传参数数据进行对比表
         */

        //查询
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",sysUserDTO.getId());
        List<SysUserRole> userRoleList =  sysUserRoleMapper.selectList(wrapper);

     /*   userRoleList.stream().forEach(userRole->{userRoleList.stream().forEach(entiy->{if (userRole.getRoleId()==entiy.getRoleId())
            userRoleList.remove(userRole);});});*/


            for ( int i=0 ;i<sysUserDTO.getRoleList().length;i++) {
                for (int k =0;k<userRoleList.size();k++){
                /**
                 * 相等的就是被选中的
                 * 留下的就是被移除的
                 */
                if ( sysUserDTO.getRoleList()[i] !=0){
                    if (userRoleList.get(k).getRoleId() == sysUserDTO.getRoleList()[i]) {
                        //移除被选中的
                        userRoleList.remove(k);
                        //留下的是新增的
                        sysUserDTO.getRoleList()[i] = 0;
                        k--;
                    }
                }
            }
        }
        userRoleList.stream().forEach(k->sysUserRoleMapper.deleteById(k.getId()));
        for (int i = 0; i < sysUserDTO.getRoleList().length; i++) {
                    if (sysUserDTO.getRoleList()[i] !=0) {
                        SysUserRole sysUserRole = new SysUserRole();
                        sysUserRole.setUserId(sysUserDTO.getId());
                        sysUserRole.setRoleId(sysUserDTO.getRoleList()[i]);
                        sysUserRoleMapper.insert(sysUserRole);
                    }
        }
        return this.baseMapper.updateById(sysUser)>0?true:false;
    }

    @CachePut(value = "user",key = "#result.id")
    @Override
    public SysUser findById(Integer id) {
        System.out.println("缓存。。。。。。。。。。。。。。。。。。。。");
        return this.getById(id);
    }

    @Override
    public UserInfo findUserInfo(Integer id) {
        return baseMapper.findById(id);
    }


}
