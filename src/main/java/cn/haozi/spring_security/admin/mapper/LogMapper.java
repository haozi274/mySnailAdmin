package cn.haozi.spring_security.admin.mapper;

import cn.haozi.spring_security.admin.entity.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 10:20
 * @Description:
 */
public interface LogMapper extends BaseMapper<Log> {


    List<Log> selectAll(IPage page, @Param("ew") QueryWrapper<Log> entityWrapper);
}
