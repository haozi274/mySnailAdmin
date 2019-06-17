package cn.haozi.spring_security.admin.entity.vo;

import cn.haozi.spring_security.admin.utils.ToolUtil;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 10:35
 * @Description:
 */
@Data
public class DataEntity<T > extends Model  implements Serializable {


    /**
     * 操作人
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;

    /**
     * 操作时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /***
     *
     * 操作地址
     */
    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime ;

    @TableField(fill = FieldFill.UPDATE)
    private Integer updateBy;

    /***
     * 逻辑删除字段
     */
    @TableLogic
    private Integer delFlag = 0;


}
