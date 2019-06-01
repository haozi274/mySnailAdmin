package cn.haozi.spring_security.admin.entity;

import cn.haozi.spring_security.admin.entity.vo.DataEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;



@Data
public class SysUser extends DataEntity<SysUser> {
  @TableId(type = IdType.AUTO)
  private Integer id;
  /***
   * 用户名
   */
  private String username;
  /**
   * 登录名
   */
  private String loginName;
  private String salt;
  private String telephone;
  private Integer icon;
  private String mail;
  private String password;
  private String remark;
  private Integer deptId;
  private Integer status;

}
