package cn.haozi.spring_security.fish.mapper;

import cn.haozi.spring_security.fish.entity.FishContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/28 17:25
 * @Description:
 */
public interface FishContentMapper extends BaseMapper<FishContent> {


    @Select("select title from fish_content where title = #{title}")
    List<FishContent> findByTitle(String title);
}
