package com.neko.Sub.controller;

import com.neko.Sub.config.AppConfig;
import com.neko.Sub.service.ProxyService;
import com.neko.Sub.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProxyService proxyService;

    @Autowired
    private AppConfig appConfig;

    @GetMapping("/api/**")
    public ResponseEntity<?> handleApiRequest(HttpServletRequest request) {
        String fullPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String pathAfterApi = fullPath.substring("/api/".length());

        String token = extractToken(pathAfterApi);

        if (token == null || token.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "缺少必填参数 token");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        if (!tokenService.isTokenValid(token, appConfig.getInvalidTimeMinutes())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "token已过期");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        tokenService.updateToken(token);

        String backendResponse = proxyService.forwardRequest(appConfig.getBackendUrl(), pathAfterApi, null);

        return ResponseEntity.ok(backendResponse);
    }

    private String extractToken(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }

        String[] parts = path.split("/");
        for (String part : parts) {
            if (part.startsWith("token=")) {
                return part.substring("token=".length());
            }
        }
        return null;
    }
}