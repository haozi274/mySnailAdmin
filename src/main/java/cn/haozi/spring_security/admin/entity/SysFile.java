package cn.haozi.spring_security.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/***
 * 系统文件
 */
@Data
public class SysFile {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String url;

    private Integer status;

}
