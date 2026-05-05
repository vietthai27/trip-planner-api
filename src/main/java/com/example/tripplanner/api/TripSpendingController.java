package com.example.tripplanner.api;

import com.example.tripplanner.common.ApiResponse;
import com.example.tripplanner.tripspending.TripSpending;
import com.example.tripplanner.tripspending.TripSpendingRequest;
import com.example.tripplanner.tripspending.TripSpendingService;
import com.example.tripplanner.tripspending.TripSpendingSummaryResponse;
import com.example.tripplanner.tripspending.TripSpendingType;
import com.example.tripplanner.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/trip-spendings")
@RequiredArgsConstructor
public class TripSpendingController {

    private final TripSpendingService tripSpendingService;

    @GetMapping
    public ApiResponse<List<TripSpending>> findAll() {
        return ApiResponse.success("Trip spendings retrieved successfully", tripSpendingService.findAll());
    }

    @GetMapping("/stages/{stageId}")
    public ApiResponse<List<TripSpending>> findAllByTripStageId(@PathVariable Long stageId) {
        return ApiResponse.success("Trip spendings retrieved successfully", tripSpendingService.findAllByTripStageId(stageId));
    }

    @GetMapping("/trips/{tripId}/without-stage")
    public ApiResponse<List<TripSpending>> findAllByTripIdWithoutStage(@PathVariable Long tripId) {
        return ApiResponse.success("Trip spendings retrieved successfully", tripSpendingService.findAllByTripIdWithoutStage(tripId));
    }

    @GetMapping("/types")
    public ApiResponse<List<TripSpendingType>> findAllTypes() {
        return ApiResponse.success("Trip spending types retrieved successfully", Arrays.asList(TripSpendingType.values()));
    }

    @GetMapping("/{id}")
    public ApiResponse<TripSpending> findById(@PathVariable Long id) {
        return ApiResponse.success("Trip spending retrieved successfully", tripSpendingService.findById(id));
    }

    @GetMapping("/trips/{tripId}/summary")
    public ApiResponse<TripSpendingSummaryResponse> summarizeByTripId(@PathVariable Long tripId) {
        return ApiResponse.success("Trip spending summary retrieved successfully", tripSpendingService.summarizeByTripId(tripId));
    }

    @PostMapping
    public ApiResponse<TripSpending> create(
            @Valid @RequestBody TripSpendingRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ApiResponse.success("Trip spending created successfully", tripSpendingService.create(request, user.getId()));
    }

    @PutMapping("/{id}")
    public ApiResponse<TripSpending> update(@PathVariable Long id, @Valid @RequestBody TripSpendingRequest request) {
        return ApiResponse.success("Trip spending updated successfully", tripSpendingService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        tripSpendingService.delete(id);
        return ApiResponse.success("Trip spending deleted successfully", null);
    }
}
