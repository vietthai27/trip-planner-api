package com.example.tripplanner.tripstage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TripStageRequest {

    @NotBlank
    private String name;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String activity;

    @NotNull
    private Long tripId;
}
