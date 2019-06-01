package cn.haozi.spring_security.admin.entity;

import cn.haozi.spring_security.admin.entity.vo.DataEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统角色
 */
@Data
public class SysRole extends DataEntity<SysRole> implements Serializable {

  @TableId(type = IdType.AUTO)
  private Integer id;
  private String name;
  private String type;
  private Integer status;
  private String remark;





}
