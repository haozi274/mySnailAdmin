package cn.haozi.spring_security.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/***
 * 系统菜单
 */
@Data
public class SysMenu implements Serializable {

  @TableId(type=IdType.AUTO)
  private Integer id;
  private String name;
  @TableField("parent_id")
  private Integer parentId;
  private Integer level;
  private String parentIds;
  private Integer sort;
  private String href;
  private String target;
  private String icon;
  private String bgColor;
  private Integer isShow;
  private Date createDate;
  private String permission;
  private Integer createBy;
  private Integer updateBy;
  private Date updateDate;
  private String remarks;
  private Integer delFlag;


}
