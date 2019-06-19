package cn.haozi.spring_security.admin.config;

import cn.haozi.spring_security.admin.base.MySysUser;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充字段
 */
@Component
@Slf4j
public class SysMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createDate = getFieldValByName("createTime",metaObject);
        Object createId = getFieldValByName("createBy",metaObject);
        Object updateDate = getFieldValByName("updateTime",metaObject);
        Object updateId = getFieldValByName("updateBy",metaObject);

        if (null == createDate) {
            setFieldValByName("createTime", new Date(),metaObject);
        }
        if (null == createId) {
            if(MySysUser.ShiroUser() != null) {
                setFieldValByName("createBy", MySysUser.id(), metaObject);
            }
        }
        if (null == updateDate) {
            setFieldValByName("updateTime", new Date(),metaObject);
        }
        if (null == updateId) {
            if (MySysUser.ShiroUser() != null) {
                setFieldValByName("updateBy", MySysUser.id(), metaObject);
            }
        }
    }

    /**
     * 更新
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime",new Date(), metaObject);
        Object updateId = getFieldValByName("updateBy",metaObject);
        if (null == updateId) {
            setFieldValByName("updateBy", MySysUser.id(), metaObject);
        }
    }
}
