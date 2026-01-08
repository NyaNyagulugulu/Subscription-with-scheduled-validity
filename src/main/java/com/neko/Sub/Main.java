package com.neko.Sub;

import com.neko.Sub.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("========================================");
        System.out.println("订阅服务已启动");
        System.out.println("Token失效时间：" + appConfig.getInvalidTimeMinutes() + "分钟");
        System.out.println("后端地址：" + appConfig.getBackendUrl());
        System.out.println("========================================");
    }
}