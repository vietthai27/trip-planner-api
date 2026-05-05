package com.example.tripplanner.tripspending;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripSpendingSummaryResponse {
    private Long tripId;
    private Integer budget;
    private Integer totalSpending;
    private Integer remain;
    private List<StageSpending> spendingByStage;
    private List<SpendingItem> spendingInTrip;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StageSpending {
        private Long tripStageId;
        private String tripStageName;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Integer totalSpending;
        private List<SpendingItem> spendings;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpendingItem {
        private Long spendingId;
        private String spendingName;
        private TripSpendingType tripSpendingType;
        private Integer amount;
        private UserItem paidBy;
        private List<UserItem> includedUsers;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserItem {
        private Long userId;
        private String username;
    }
}
