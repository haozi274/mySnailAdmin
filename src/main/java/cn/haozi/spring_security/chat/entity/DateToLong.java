package cn.haozi.spring_security.chat.entity;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

public class DateToLong implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if(object instanceof Date){
            SerializeWriter out = serializer.out;
            long time=((Date) object).getTime();
            out.writeLong(time);
        }
    }

}
