package com.example.tripplanner.api;

import com.example.tripplanner.common.ApiResponse;
import com.example.tripplanner.common.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/modder")
@RequiredArgsConstructor
public class ModderController {

    private final MessageService messageService;

    @GetMapping("/review")
    public ApiResponse<Map<String, String>> review() {
        return ApiResponse.success(
                messageService.get("modder.review.success"),
                Map.of("access", "MODDER or ADMIN")
        );
    }
}
