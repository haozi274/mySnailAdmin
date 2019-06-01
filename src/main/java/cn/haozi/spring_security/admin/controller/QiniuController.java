package cn.haozi.spring_security.admin.controller;

import cn.haozi.spring_security.admin.config.ConstantQiniu;
import cn.haozi.spring_security.admin.entity.SysFile;
import cn.haozi.spring_security.admin.service.SysFileService;
import cn.haozi.spring_security.admin.utils.RestResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/***
 * 七牛云上传
 */
@RestController
@RequestMapping("/sys/qiniu")
public class QiniuController {

    @Autowired
    private ConstantQiniu constantQiniu;
    @Autowired
    private SysFileService sysFileService;
    /**
     * 上传文件到七牛云存储
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/qiniu")
    @ResponseBody
    public RestResponse uploadImgQiniu(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
        String path = uploadQNImg(inputStream, UUID.randomUUID().toString().replace("-","")); // KeyUtil.genUniqueKey()生成图片的随机名
        path = "http://"+path;
        SysFile sysFile = new SysFile();
        sysFile.setUrl(path);
        sysFile.setStatus(0);
        /***
         * 根据图片地址查询有没有此图片
         */
        QueryWrapper<SysFile> wrapper = new QueryWrapper<>();
        wrapper.eq("url",path);
        SysFile file = sysFileService.getOne(wrapper);
        RestResponse restResponse = new RestResponse();
        if (file == null) {
            sysFileService.save(sysFile);
            restResponse.setData(sysFile);
        }else{
            restResponse.setData(file);
        }

        return restResponse;
    }

    /**
     * 将图片上传到七牛云
     * Zone.zone2() 华南
     */
    private String uploadQNImg(FileInputStream file, String key) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传

        try {
            Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
            String upToken = auth.uploadToken(constantQiniu.getBucketName());
            try {
                Response response = uploadManager.put(file, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                String returnPath = constantQiniu.getPath() + "/" + putRet.key;
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
        }
    }

