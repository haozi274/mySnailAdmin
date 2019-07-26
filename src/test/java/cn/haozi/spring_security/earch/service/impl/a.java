package cn.haozi.spring_security.earch.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

public class a {

    public static void main(String[] args) {
        String str = "asdasd13212dxasdasd1dasdas12dasda1";
        String   sign = DigestUtil.md5Hex(str);
        sign = sign.toLowerCase();
        System.out.println(sign.length());

        String object = "{ code:0,data:[]}";
        JSON json = JSONUtil.parse(object);
        System.out.println(json.getByPath("code"));
    }
}
