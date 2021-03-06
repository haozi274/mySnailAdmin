package cn.haozi.spring_security.common.controller;

import cn.haozi.spring_security.admin.config.GeetestConfig;
import cn.haozi.spring_security.admin.config.TCaptchaVerifyConfig;
import cn.haozi.spring_security.admin.entity.SysFile;
import cn.haozi.spring_security.admin.entity.dto.VeryfyDTO;
import cn.haozi.spring_security.admin.utils.RestResponse;
import cn.haozi.spring_security.common.VerifyTicket;
import cn.haozi.spring_security.common.sdk.GeetestLib;
import com.alibaba.fastjson.JSON;
import com.xiaoleilu.hutool.json.JSONException;
import com.xiaoleilu.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @Auther: 陈思浩
 * @Date: 2019/5/21 20:53
 * @Description: 极验证
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/verify")
public class VerifyController {

//    private RedisUtil redisUtil;
    StringRedisTemplate stringRedisTemplate;



    private GeetestConfig geetestConfig;

    private TCaptchaVerifyConfig tCaptchaVerifyConfig;

    @GetMapping("/first")
    public Object first(HttpServletRequest request) {

       GeetestLib gtSdk = new GeetestLib(geetestConfig.getGeetestId(), geetestConfig.getGeetestKey(),
               geetestConfig.isNewfailback());

        String resStr = "{}";

        String userid = "test";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        stringRedisTemplate.opsForValue().set(gtSdk.gtServerStatusSessionKey,gtServerStatus+"",2000);
        //将userid设置到session中
        stringRedisTemplate.opsForValue().set("userid",userid+"",2000);
        resStr = gtSdk.getResponseStr();
          return resStr;

    }

    /***
     * 极验证：验证成功进行登录
     * @param veryfyDTO
     * @return
     */
    @PostMapping("/login")
    public RestResponse tow( VeryfyDTO veryfyDTO){
        RestResponse data = new RestResponse();
        GeetestLib gtSdk = new GeetestLib(geetestConfig.getGeetestId(), geetestConfig.getGeetestKey(),
                geetestConfig.isNewfailback());

        String challenge = veryfyDTO.getGeetestChallenge();
        String validate = veryfyDTO.getGeetestValidate();
        String seccode = veryfyDTO.getGeetestSeccode();

        String gt_server_status_code = stringRedisTemplate.opsForValue().get(gtSdk.gtServerStatusSessionKey);
        //从session中获取userid
        String userid =  stringRedisTemplate.opsForValue().get("userid");
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        int gtResult = 0;

        if ("1".equals(gt_server_status_code)) {
            //gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证

            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }
        if (gtResult == 1) {
            return login(veryfyDTO.getUserName(),veryfyDTO.getPassword());

        } else {
            // 验证失败
            try {
                data.put("status", "fail");
                data.put("version", gtSdk.getVersionInfo());
                return data;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 腾讯防水墙
     * @param verifyTicket
     * @param request
     * @return
     */
    @PostMapping("/ticket")
    public  RestResponse verifyTicket( VerifyTicket verifyTicket, HttpServletRequest request) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet;
        CloseableHttpResponse response = null;
        /**
         *获取IP地址
         */
//        String userIp = IpUtil.getIpAddr(request);
        RestResponse data = new RestResponse();

        try {
            httpGet = new HttpGet(String.format(tCaptchaVerifyConfig.getVerify_uri(),
                    tCaptchaVerifyConfig.getApp_id(),
                    tCaptchaVerifyConfig.getApp_secret(),
                    URLEncoder.encode(verifyTicket.getTicket(), "UTF-8"),
                    URLEncoder.encode(verifyTicket.getRandstr(), "UTF-8"),
                    URLEncoder.encode("127.0.0.1", "UTF-8")
            ));
            response = httpclient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String res = EntityUtils.toString(entity);
                System.out.println(res); // 临时输出

                com.alibaba.fastjson.JSONObject result = JSON.parseObject(res);
                // 返回码
                int code = result.getInteger("response");
                // 恶意等级
                int evilLevel = result.getInteger("evil_level");

                // 验证成功
                if (code == 1) {
                            return login(verifyTicket.getUsername(),verifyTicket.getPassword());
                }else{
                    // 验证失败
                    data.setMessage("重新滑动图片");
                }
            }
        } catch (java.io.IOException e) {
            // 忽略
        } finally {
            try {
                response.close();
            } catch (Exception ignore) {
            }
        }
        return data;
    }


    public  static RestResponse login(String usernmae,String password){
        /**
         * 登录
         */
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(usernmae,password,true);
        Map<String,String> map = new LinkedHashMap<String,String>();
        String error = "";
        try {
            user.login(token);
            user.getPrincipal();
            if (user.isAuthenticated()) {
                return RestResponse.success();
            }
        }catch (IncorrectCredentialsException e) {
            error = "登录密码错误.";
        } catch (ExcessiveAttemptsException e) {
            error = "登录失败次数过多";
        } catch (LockedAccountException e) {
            error = "帐号已被锁定.";
        } catch (DisabledAccountException e) {
            error = "帐号已被禁用.";
        } catch (ExpiredCredentialsException e) {
            error = "帐号已过期.";
        } catch (UnknownAccountException e) {
            error = "帐号不存在";
        } catch (UnauthorizedException e) {
            error = "您没有得到相应的授权！";
        }
        if (StringUtils.isBlank(error)) {
            return RestResponse.success().setData(map);
        }else{
            return RestResponse.failure(error);
        }
    }
}