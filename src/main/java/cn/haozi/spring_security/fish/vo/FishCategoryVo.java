package cn.haozi.spring_security.fish.vo;

import cn.haozi.spring_security.fish.entity.FishCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/8/1 09:10
 * @Description:
 */
@Data
public class FishCategoryVo extends FishCategory {

    private List<FishCategoryVo> list;

}
