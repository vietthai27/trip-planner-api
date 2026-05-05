package com.example.tripplanner.api;

import com.example.tripplanner.common.ApiResponse;
import com.example.tripplanner.tripstage.TripStage;
import com.example.tripplanner.tripstage.TripStageRequest;
import com.example.tripplanner.tripstage.TripStageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trip-stages")
@RequiredArgsConstructor
public class TripStageController {

    private final TripStageService tripStageService;

    @GetMapping
    public ApiResponse<List<TripStage>> findAll() {
        return ApiResponse.success("Trip stages retrieved successfully", tripStageService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<TripStage> findById(@PathVariable Long id) {
        return ApiResponse.success("Trip stage retrieved successfully", tripStageService.findById(id));
    }

    @PostMapping
    public ApiResponse<TripStage> create(@Valid @RequestBody TripStageRequest request) {
        return ApiResponse.success("Trip stage created successfully", tripStageService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<TripStage> update(@PathVariable Long id, @Valid @RequestBody TripStageRequest request) {
        return ApiResponse.success("Trip stage updated successfully", tripStageService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        tripStageService.delete(id);
        return ApiResponse.success("Trip stage deleted successfully", null);
    }
}
