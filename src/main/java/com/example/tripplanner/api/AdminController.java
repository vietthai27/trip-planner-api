package com.example.tripplanner.api;

import com.example.tripplanner.common.ApiResponse;
import com.example.tripplanner.common.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MessageService messageService;

    @GetMapping("/dashboard")
    public ApiResponse<Map<String, String>> dashboard() {
        return ApiResponse.success(
                messageService.get("admin.dashboard.success"),
                Map.of("access", "ADMIN only")
        );
    }
}
