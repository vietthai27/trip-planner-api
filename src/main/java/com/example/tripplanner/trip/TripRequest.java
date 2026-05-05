package com.example.tripplanner.trip;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class TripRequest {

    @NotBlank
    private String title;

    private String status;
    private Integer budget;
    private String startLocation;
    private String endLocation;
    private LocalDateTime startTime;
    private Integer distance;
    private Set<Long> userIds;
}
