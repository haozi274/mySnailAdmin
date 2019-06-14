package cn.haozi.spring_security.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * 定时任务
 */
@Data
public class SysTask implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 定时任务名称
     */
    private String taskName;
    /**
     * 执行时间
     */
    private String cron;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 类型
     */
    private String type;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建人
     */
    private String updateBy;
    /**
     * 修改人
     *
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean delFlag;

}
