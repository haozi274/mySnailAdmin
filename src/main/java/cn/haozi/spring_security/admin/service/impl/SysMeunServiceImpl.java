package cn.haozi.spring_security.admin.service.impl;

import cn.haozi.spring_security.admin.entity.SysMenu;
import cn.haozi.spring_security.admin.entity.vo.TreeEntity;
import cn.haozi.spring_security.admin.mapper.SysMeunMapper;
import cn.haozi.spring_security.admin.service.SysMeunService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/11 22:44
 * @Description:
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SysMeunServiceImpl extends ServiceImpl<SysMeunMapper, SysMenu> implements SysMeunService {

    private SysMeunMapper meunMapper;

    /**
     * 查询树形菜单，分等级展示
     *
     * @return
     */
    @Cacheable(value  = "menuShow",key = "keyGenerator")
    public List<TreeEntity> findAllList(boolean flag) {
        System.out.println("缓存。。。。。。。。。。。。。。。。。。。。。。。");
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        if (!flag) {
            wrapper.eq("is_show", "1");
        }
        wrapper.orderByDesc("sort");
        IPage<SysMenu> page = this.page(new Page<>(1, 1000), wrapper);
        List<TreeEntity> treeEntityList = new ArrayList<>();
        return selectMeun(null, page.getRecords(), treeEntityList);
    }

    @Override
     @Cacheable(cacheNames = "meun",key="#result.id",unless = "#result.id==null")
    public SysMenu findById(Integer id) {
        return this.getById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @CachePut(value = "meun", key = "#id", unless = "#id == null ")
    @Override
    public boolean insert(SysMenu sysMenu) {
        return this.save(sysMenu);
    }


    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @CacheEvict(value = "meun",key = "#id")
    public boolean deleteById(Integer id) {
        this.baseMapper.deleteByParentId(id);
        if (this.removeById(id)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteByParentId(Integer id) {
        return true;
    }

    @Override
    public List<SysMenu> findMenuByRoleId(Integer roleId) {

        return this.baseMapper.findMenuByRoleId(roleId);
    }
    @Cacheable(value  = "menuShow",key = "#userId")
    @Override
    public List<TreeEntity> findByUserId(Integer userId) {
        System.out.println("缓存。。。。。。。。。。。。。。。。。。。。。。。");
        List<SysMenu> list = baseMapper.findByUserId(userId);
        List<TreeEntity> treeEntityList = new ArrayList<>();
        return selectMeun(null, list, treeEntityList);
    }

    /***
     * 递归查询菜单等级
     * @param
     * @param sysMenuList
     * @param treeEntityList
     * @return
     */
    public static List<TreeEntity> selectMeun(TreeEntity treeEntity, List<SysMenu> sysMenuList, List<TreeEntity> treeEntityList) {
        List<TreeEntity> childList = new ArrayList<>();
        Integer pid = treeEntity == null ? -1 : treeEntity.getId();
        for (SysMenu meun : sysMenuList) {
            /***
             * 如果当前的ID是当前菜单的父类ID,那么就装入对象中
             */
            if (pid == meun.getParentId()) {
                TreeEntity entity = new TreeEntity();
                entity.setId(meun.getId());
                entity.setName(meun.getName());
                entity.setHref(meun.getHref());
                entity.setIcon(meun.getIcon());
                entity.setParentId(meun.getParentId());
                childList.add(entity);
                /**
                 * 执行方法判断是否有下级
                 */
                selectMeun(entity, sysMenuList, treeEntityList);
            }
        }

        if (treeEntity != null) {
            treeEntity.setList(childList);
        } else {
            treeEntityList = childList;
        }
        return treeEntityList;
    }
}
