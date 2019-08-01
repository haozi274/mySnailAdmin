package cn.haozi.spring_security.fish.service.impl;

import cn.haozi.spring_security.fish.entity.FishCategory;
import cn.haozi.spring_security.fish.mapper.FishCategoryMapper;
import cn.haozi.spring_security.fish.service.FishCategoryService;
import cn.haozi.spring_security.fish.vo.FishCategoryVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/31 13:27
 * @Description:
 */
@Service
public class FishCategoryServiceImpl extends ServiceImpl<FishCategoryMapper, FishCategory> implements FishCategoryService {

    public List<FishCategoryVo> findAllList() {
        List<FishCategoryVo> treeEntityList = new ArrayList<>();
        List<FishCategory> list =  baseMapper.selectList(null);
        return selectMeun(null, list, treeEntityList);
    }

    /***
     * 递归查询菜单等级
     * @param
     * @param sysMenuList
     * @param treeEntityList
     * @return
     */
    public static List<FishCategoryVo> selectMeun(FishCategoryVo treeEntity, List<FishCategory> sysMenuList, List<FishCategoryVo> treeEntityList) {
        List<FishCategoryVo> childList = new ArrayList<>();
        Integer pid = treeEntity == null ? -1 : treeEntity.getId();
        for (FishCategory meun : sysMenuList) {
            /***
             * 如果当前的ID是当前菜单的父类ID,那么就装入对象中
             */
            if (pid == meun.getParentId()) {
                FishCategoryVo entity = new FishCategoryVo();
                entity.setId(meun.getId());
                entity.setType(meun.getType());
                entity.setUrl(meun.getUrl());
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
