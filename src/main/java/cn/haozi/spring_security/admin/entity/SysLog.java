package cn.haozi.spring_security.admin.entity;

import lombok.Data;

@Data
public class SysLog {

  private Integer id;
  private long type;
  private String target;
  private String oldValue;
  private String newValue;
  private long status;
  private String operator;
  private java.sql.Timestamp operateTime;
  private String operateIp;


}
