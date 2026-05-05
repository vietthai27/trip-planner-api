package com.example.tripplanner.tripspending;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TripSpendingRequest {

    @NotBlank
    private String name;

    @NotNull
    private Integer amount;

    @NotNull
    private Long userId;

    @NotNull
    private Long tripId;

    private Long tripStageId;

    private TripSpendingType tripSpendingType;

    private Set<Long> includedUserIds;
}
