package cn.haozi.spring_security.admin.entity;

import cn.haozi.spring_security.admin.entity.vo.DataEntity;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 10:10
 * @Description: 记录访问api接口日志
 */
@Data
public class Log extends BaseRowModel {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ExcelProperty(value = "操作",index = 0)
    private String operation; //操作

    @ExcelProperty(value = "方法名",index = 1)
    private String method; //方法名

    @ExcelProperty(value = "参数",index = 2)
    private String params; //参数


    @TableField(exist = false)
    private String loginName;

    /**
     * 操作人
     */
    private Integer createBy;

    /**
     * 操作时间
     */
    @ExcelProperty(value = "操作时间",index = 3)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /***
     *
     * 操作地址
     */
    @ExcelProperty(value = "操作地址",index =4)
    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime ;

    private Integer updateBy;

    /***
     * 逻辑删除字段
     */
    @TableLogic
    private Integer delFlag = 0;
}
