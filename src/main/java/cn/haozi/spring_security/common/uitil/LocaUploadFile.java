package cn.haozi.spring_security.common.uitil;

import cn.haozi.spring_security.admin.entity.SysFile;
import cn.haozi.spring_security.admin.service.SysFileService;
import cn.haozi.spring_security.admin.utils.RestResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Component
@Slf4j
public class LocaUploadFile {
    @Autowired
    private SysFileService fileService;

    @Value("${file.filePath}")
    private String filePath;

    @RequestMapping(value = "/upload")
    public RestResponse upload(@RequestParam("file") MultipartFile file) {

        RestResponse data = new RestResponse();
        /**
         * 本地上传文件
         */
        try {
            if (file.isEmpty()) {
                return RestResponse.failure("上传文件失败");
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);
            // 设置文件存储路径
            // String filePath = "D:/snail/file/";
            String name = filePath+ fileName;
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() +name;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            /**
             * 保存数据库
             * 返回id
             */
            SysFile sysFile = new SysFile();
            sysFile.setUrl(filePath+fileName);
            fileService.save(sysFile);
            System.out.println(sysFile);
            data.setData(sysFile);
            return data;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RestResponse.failure("上传文件失败");
    }

    @PostMapping("/batch")
    public RestResponse handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            // String filePath = "D:/snail/file/";
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(filePath + file.getOriginalFilename())));//设置文件路径及名字
                    stream.write(bytes);// 写入
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    log.error("第  "+ i +"  个文件上传失败 ==> ",e.getMessage());
                    return RestResponse.failure("第 " + i + " 个文件上传失败 ==> ") ;
                }
            } else {
                log.error("第  "+ i +"个文件上传失败因为文件为空");
                return RestResponse.failure("第 " + i + " 个文件上传失败因为文件为空") ;
            }
        }
        return RestResponse.success();
    }

    /**
     * 下载
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "dalaoyang.jpeg";// 文件名
        if (fileName != null) {
            //设置文件路径
            File file = new File("/Users/dalaoyang/Documents/dalaoyang.jpeg");
            //File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }

    public static void main(String[] args) {
        try {
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            System.out.println(path+"/static/uploadFile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}