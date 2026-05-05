package com.example.tripplanner.api;

import com.example.tripplanner.common.ApiResponse;
import com.example.tripplanner.vehicle.Vehicle;
import com.example.tripplanner.vehicle.VehicleService;
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
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ApiResponse<List<Vehicle>> findAll() {
        return ApiResponse.success("Vehicles retrieved successfully", vehicleService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Vehicle> findById(@PathVariable Long id) {
        return ApiResponse.success("Vehicle retrieved successfully", vehicleService.findById(id));
    }

    @PostMapping
    public ApiResponse<Vehicle> create(@Valid @RequestBody Vehicle vehicle) {
        return ApiResponse.success("Vehicle created successfully", vehicleService.create(vehicle));
    }

    @PutMapping("/{id}")
    public ApiResponse<Vehicle> update(@PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
        return ApiResponse.success("Vehicle updated successfully", vehicleService.update(id, vehicle));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return ApiResponse.success("Vehicle deleted successfully", null);
    }
}
