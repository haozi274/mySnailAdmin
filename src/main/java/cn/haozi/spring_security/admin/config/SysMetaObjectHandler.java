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
        Object createDate = getFieldValByName("createDate",metaObject);
        Object createId = getFieldValByName("createId",metaObject);
        Object updateDate = getFieldValByName("updateDate",metaObject);
        Object updateId = getFieldValByName("updateId",metaObject);

        if (null == createDate) {
            setFieldValByName("createDate", new Date(),metaObject);
        }
        if (null == createId) {
            if(MySysUser.ShiroUser() != null) {
                setFieldValByName("createId", MySysUser.id(), metaObject);
            }
        }
        if (null == updateDate) {
            setFieldValByName("updateDate", new Date(),metaObject);
        }
        if (null == updateId) {
            if (MySysUser.ShiroUser() != null) {
                setFieldValByName("updateId", MySysUser.id(), metaObject);
            }

        }
    }

    /**
     * 更新
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateDate",new Date(), metaObject);
        Object updateId = getFieldValByName("updateId",metaObject);
        if (null == updateId) {
            setFieldValByName("updateId", MySysUser.id(), metaObject);
        }
    }
}
