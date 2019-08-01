package cn.haozi.spring_security.common.task;

import cn.haozi.spring_security.fish.service.IShuiChanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Auther: 陈思浩
 * @Date: 2019/8/1 08:58
 * @Description:
 */
@Slf4j
@Component("fishContentTask")
public class FishContentTask {

    @Autowired
    private IShuiChanService shuiChanService;

    public void test(){
      log.info("开始时间 => FishContentTask");
      long start = System.currentTimeMillis();
        shuiChanService.reptileCategory(null);
        long end = System.currentTimeMillis();
        log.info("结束时间 => " ,end -start);
    }
}
