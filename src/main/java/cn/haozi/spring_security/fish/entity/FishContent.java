package cn.haozi.spring_security.fish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/28 17:21
 * @Description:
 */
@Data
public class FishContent implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer categoryId;

    private String title;

    private String source;

    private Integer viewCount;

    private String content;

    private Date createTime;

    private Date updateTime;
}
