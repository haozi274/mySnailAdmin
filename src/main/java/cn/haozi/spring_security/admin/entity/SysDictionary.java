package cn.haozi.spring_security.admin.entity;

import cn.haozi.spring_security.admin.entity.vo.DataEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysDictionary extends DataEntity<SysDictionary> {

  @TableId(type = IdType.AUTO)
  private Integer id;
  /**
   * 类型
   */
  private String type;
  /**
   * 标签
   */
  private String label;
  private Integer parentId;
  private String value;
  private String remark;


}
