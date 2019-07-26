package cn.haozi.spring_security.admin.controller;

import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.service.SysUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

import static org.junit.Assert.*;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/6 10:49
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserControllerTest {

    @Autowired
    private SysUserService sysUserService;
    /**
     * 序列化
     * @throws Exception
     */
    @Test
    public void list() throws Exception {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("haozi");
        sysUser.setPassword("123");
        ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(new File("D://fle.txt")));
        objectOutput.writeObject(sysUser);
        objectOutput.close();
    }

    /***
     * 反序列化
     * @throws Exception
     */
    @Test
    public void test2()throws Exception{
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("D://fle.txt")));
        SysUser sysUser = (SysUser) objectInputStream.readObject();
        System.out.println(sysUser.toString());
    }

    @Test
    public void test3(){
        sysUserService.page(new Page<>(1,2));
        System.out.println("aaa");
    }
    @Test
    public void test4(){
        A<String,Integer> a = Integer::valueOf;
        Integer v = a.play("12312");
        System.out.println(v);
    }
}

interface  A<T,E>{

     E play(T a);
}
class Demo{

}
class Demo2 extends  Demo{

}


interface B<F extends Demo> {

}
