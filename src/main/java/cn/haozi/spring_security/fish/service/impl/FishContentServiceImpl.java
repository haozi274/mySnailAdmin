package cn.haozi.spring_security.fish.service.impl;

import cn.haozi.spring_security.fish.entity.FishContent;
import cn.haozi.spring_security.fish.mapper.FishContentMapper;

import cn.haozi.spring_security.fish.service.FishContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/28 17:26
 * @Description:
 */
@Service
public class FishContentServiceImpl extends ServiceImpl<FishContentMapper, FishContent> implements FishContentService {

    @Override
    public List<FishContent> findByTitle(String title) {
        return baseMapper.findByTitle(title);
    }
}
