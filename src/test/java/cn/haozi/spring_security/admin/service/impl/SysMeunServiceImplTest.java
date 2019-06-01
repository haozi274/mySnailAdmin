package cn.haozi.spring_security.admin.service.impl;

import cn.haozi.spring_security.admin.entity.vo.TreeEntity;
import cn.haozi.spring_security.admin.service.SysMeunService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/12 16:22
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysMeunServiceImplTest {

    @Autowired
    private SysMeunService sysMeunService;

    @Test
    public void selectMeun() {
        List<TreeEntity> list = sysMeunService.findAllList(false);
        System.out.println(list);
     }
}