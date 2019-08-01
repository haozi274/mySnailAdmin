package cn.haozi.spring_security.fish.service;

import cn.haozi.spring_security.fish.entity.FishContent;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/28 17:26
 * @Description:
 */
public interface FishContentService extends IService<FishContent> {

    List<FishContent> findByTitle(String title);
}
