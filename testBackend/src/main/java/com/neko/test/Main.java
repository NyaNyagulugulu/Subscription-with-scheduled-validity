package com.neko.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/api/{path}/**")
    public Map<String, Object> apiHandler(
            @PathVariable String path,
            @RequestParam String token,
            @RequestParam(required = false) String flag) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Request received successfully");
        response.put("path", path);
        response.put("token", token);
        response.put("flag", flag != null ? flag : "not provided");
        response.put("timestamp", System.currentTimeMillis());
        response.put("data", Map.of(
                "id", 12345,
                "name", "Test Backend",
                "description", "这是一个模拟后端接口"
        ));

        return response;
    }
}