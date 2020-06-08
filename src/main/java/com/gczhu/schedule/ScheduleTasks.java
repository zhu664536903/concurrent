package com.gczhu.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

/**
 * spring 任务调度
 */
@Component
public class ScheduleTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduleTasks.class);
    SimpleDateFormat s =new  SimpleDateFormat("HH:mm:ss");

    final String cron="10,20 * * * * *";
    @Scheduled(cron = cron)
    public void doService(){
        log.info("The time is now {}",s.format(new Date()));
        Collections.sort(null);
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void doService2(){
        log.info("The time is now {}",s.format(new Date()));
    }

}
