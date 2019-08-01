package cn.haozi.spring_security.fish.dto;

import cn.haozi.spring_security.fish.entity.FishCategory;
import lombok.Data;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/31 15:51
 * @Description:
 */
@Data
public class FishCategoryDTO extends FishCategory {

    private Integer page;

    private Integer limit;

    private String parentName;
}
