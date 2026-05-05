package com.example.tripplanner.api;

import com.example.tripplanner.common.ApiResponse;
import com.example.tripplanner.trip.Trip;
import com.example.tripplanner.trip.TripRequest;
import com.example.tripplanner.trip.TripService;
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

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping
    public ApiResponse<List<Trip>> findAll() {
        return ApiResponse.success("Trips retrieved successfully", tripService.findAll());
    }

    @GetMapping("/me")
    public ApiResponse<List<Trip>> findAllByLoggedInUser(@AuthenticationPrincipal User user) {
        return ApiResponse.success("Trips retrieved successfully", tripService.findAllByUserId(user.getId()));
    }

    @GetMapping("/{tripId}/users")
    public ApiResponse<List<User>> findUsersByTripId(@PathVariable Long tripId) {
        return ApiResponse.success("Trip users retrieved successfully", tripService.findUsersByTripId(tripId));
    }

    @GetMapping("/{id}")
    public ApiResponse<Trip> findById(@PathVariable Long id) {
        return ApiResponse.success("Trip retrieved successfully", tripService.findById(id));
    }

    @PostMapping
    public ApiResponse<Trip> create(@Valid @RequestBody TripRequest request) {
        return ApiResponse.success("Trip created successfully", tripService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Trip> update(@PathVariable Long id, @Valid @RequestBody TripRequest request) {
        return ApiResponse.success("Trip updated successfully", tripService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        tripService.delete(id);
        return ApiResponse.success("Trip deleted successfully", null);
    }
}
