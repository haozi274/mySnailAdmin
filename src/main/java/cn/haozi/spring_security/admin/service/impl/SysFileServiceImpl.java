package cn.haozi.spring_security.admin.service.impl;

import cn.haozi.spring_security.admin.entity.SysFile;
import cn.haozi.spring_security.admin.mapper.SysFileMapper;
import cn.haozi.spring_security.admin.service.SysFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {
}
