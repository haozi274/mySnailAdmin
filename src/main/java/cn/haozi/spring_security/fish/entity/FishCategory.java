package cn.haozi.spring_security.fish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/31 13:17
 * @Description:  文章分类
 */
@Data
public class FishCategory implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 类型名称
     */
    private String type;
    /**
     *
     * 父类ID
     */
    private Integer parentId;
    /**
     * 图标
     */
    private String icon;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 链接地址
     */
    private String url;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer delFlag;
}
