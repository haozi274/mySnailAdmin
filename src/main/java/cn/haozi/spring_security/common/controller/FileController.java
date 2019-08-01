package cn.haozi.spring_security.common.controller;

import cn.haozi.spring_security.admin.controller.QiniuUtils;
import cn.haozi.spring_security.admin.entity.SysFile;
import cn.haozi.spring_security.admin.service.SysFileService;
import cn.haozi.spring_security.admin.utils.RestResponse;
import cn.haozi.spring_security.common.uitil.LocaUploadFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/sys/file")
public class FileController {
    @Autowired
    private SysFileService fileService;
    @Autowired
    private LocaUploadFile locaUploadFile;
    @Autowired
    private QiniuUtils qiniuUtils;


    /**
     * 文件上传路劲
     */
    @Value("${file.filePath}")
    private String filePath;
    /**
     * 是否开启本地上传
     */
    @Value("${file.openLocal}")
    private Boolean openLocal;

    @RequestMapping(value = "/upload")
    public RestResponse upload(@RequestParam("file") MultipartFile file) {
            if (openLocal) {
                 return   locaUploadFile.upload(file);
            }else{
                try {
                    return qiniuUtils.uploadImgQiniu(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return RestResponse.failure("上传失败");
    }

    /**
     * 下载
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {

        return "下载失败";
    }
}