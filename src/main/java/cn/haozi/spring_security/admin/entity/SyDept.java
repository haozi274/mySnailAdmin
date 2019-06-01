package cn.haozi.spring_security.admin.entity;

import lombok.Data;

@Data
public class SyDept {

  private Integer id;
  private String name;
  private String level;
  private Integer seq;
  private String remark;
  private Integer parentId;
  private String operator;
  private java.sql.Timestamp operateTime;
  private String operateIp;



}
