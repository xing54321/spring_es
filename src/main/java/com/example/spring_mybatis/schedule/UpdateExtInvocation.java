package com.example.spring_mybatis.schedule;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.spring_mybatis.service.ExtInvocationService;

@Configuration      // 标记配置类，兼备Component的效果。
@EnableScheduling   // 开启定时任务
public class UpdateExtInvocation {
	@Autowired
	private  ExtInvocationService extInvocationService;
    @Scheduled(cron = "0/5 * * * * ?")
    private void configureTasks() {
        System.out.println("执行静态定时任务时间: " + LocalDateTime.now());
        extInvocationService.aggExtInvocation();
    }
}
