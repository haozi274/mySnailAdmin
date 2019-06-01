package cn.haozi.spring_security.admin.entity;

import cn.haozi.spring_security.admin.entity.vo.DataEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 10:10
 * @Description: 记录访问api接口日志
 */
@Data
public class Log extends DataEntity<Log> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String operation; //操作

    private String method; //方法名

    private String params; //参数

    @TableField(exist = false)
    private String loginName;
}
