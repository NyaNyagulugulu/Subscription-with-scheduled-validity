package com.neko.Sub;

import com.neko.Sub.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private AppConfig appConfig;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        copyApplicationConfig();

        System.out.println("========================================");
        System.out.println("订阅服务已启动");
        System.out.println("Token失效时间：" + appConfig.getInvalidTimeMinutes() + "分钟");
        System.out.println("后端地址：" + appConfig.getBackendUrl());
        System.out.println("========================================");
    }

    private void copyApplicationConfig() {
        try {
            ClassPathResource resource = new ClassPathResource("application.yml");
            File targetFile = new File("application.yml");

            if (!targetFile.exists()) {
                Files.copy(resource.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("配置文件已释放到运行目录：" + targetFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("配置文件释放失败：" + e.getMessage());
        }
    }
}