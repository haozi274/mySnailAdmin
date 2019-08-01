package cn.haozi.spring_security.fish.service;

import cn.haozi.spring_security.fish.entity.FishCategory;
import cn.haozi.spring_security.fish.vo.FishCategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/31 13:27
 * @Description:
 */
public interface FishCategoryService extends IService<FishCategory> {

    List<FishCategoryVo> findAllList();
}
