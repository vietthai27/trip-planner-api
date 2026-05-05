package com.example.tripplanner.tripspending;

import com.example.tripplanner.trip.Trip;
import com.example.tripplanner.trip.TripRepository;
import com.example.tripplanner.tripstage.TripStage;
import com.example.tripplanner.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripSpendingService {

    private final TripSpendingRepository tripSpendingRepository;
    private final TripRepository tripRepository;
    private final TripSpendingMapper tripSpendingMapper;

    public List<TripSpending> findAll() {
        return tripSpendingRepository.findAll();
    }

    public List<TripSpending> findAllByTripStageId(Long tripStageId) {
        return tripSpendingRepository.findByTripStage_Id(tripStageId);
    }

    public List<TripSpending> findAllByTripIdWithoutStage(Long tripId) {
        return tripSpendingRepository.findByTripIdAndTripStageIsNull(tripId);
    }

    public TripSpending findById(Long id) {
        return tripSpendingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip spending not found: " + id));
    }

    public TripSpending create(TripSpendingRequest request) {
        return tripSpendingRepository.save(tripSpendingMapper.toEntity(request));
    }

    public TripSpending create(TripSpendingRequest request, Long currentUserId) {
        if (request.getUserId() == null) {
            request.setUserId(currentUserId);
        }
        return create(request);
    }

    public TripSpending update(Long id, TripSpendingRequest request) {
        TripSpending tripSpending = findById(id);
        tripSpendingMapper.updateEntity(request, tripSpending);
        return tripSpendingRepository.save(tripSpending);
    }

    @Transactional(readOnly = true)
    public TripSpendingSummaryResponse summarizeByTripId(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found: " + tripId));
        List<TripSpending> spendings = tripSpendingRepository.findByTripId(tripId);

        int budget = trip.getBudget() == null ? 0 : trip.getBudget();
        int totalSpending = spendings.stream()
                .mapToInt(spending -> spending.getAmount() == null ? 0 : spending.getAmount())
                .sum();

        return TripSpendingSummaryResponse.builder()
                .tripId(trip.getId())
                .budget(budget)
                .totalSpending(totalSpending)
                .remain(budget - totalSpending)
                .spendingByStage(summarizeByStage(spendings))
                .spendingInTrip(summarizeSpendingInTrip(spendings))
                .build();
    }

    public void delete(Long id) {
        if (!tripSpendingRepository.existsById(id)) {
            throw new EntityNotFoundException("Trip spending not found: " + id);
        }
        tripSpendingRepository.deleteById(id);
    }

    private List<TripSpendingSummaryResponse.StageSpending> summarizeByStage(List<TripSpending> spendings) {
        Map<StageKey, List<TripSpending>> spendingsByStage = spendings.stream()
                .filter(spending -> spending.getTripStage() != null)
                .collect(Collectors.groupingBy(
                        spending -> toStageKey(spending.getTripStage())
                ));

        return spendingsByStage.entrySet().stream()
                .map(entry -> TripSpendingSummaryResponse.StageSpending.builder()
                        .tripStageId(entry.getKey().id())
                        .tripStageName(entry.getKey().name())
                        .startTime(entry.getKey().startTime())
                        .endTime(entry.getKey().endTime())
                        .totalSpending(sumAmount(entry.getValue()))
                        .spendings(toSpendingItems(entry.getValue()))
                        .build())
                .sorted(Comparator.comparing(
                        TripSpendingSummaryResponse.StageSpending::getTripStageName,
                        Comparator.nullsLast(String::compareToIgnoreCase)
                ))
                .toList();
    }

    private List<TripSpendingSummaryResponse.SpendingItem> summarizeSpendingInTrip(List<TripSpending> spendings) {
        return toSpendingItems(spendings.stream()
                .filter(spending -> spending.getTripStage() == null)
                .toList());
    }

    private int sumAmount(List<TripSpending> spendings) {
        return spendings.stream()
                .mapToInt(spending -> spending.getAmount() == null ? 0 : spending.getAmount())
                .sum();
    }

    private List<TripSpendingSummaryResponse.SpendingItem> toSpendingItems(List<TripSpending> spendings) {
        return spendings.stream()
                .map(spending -> TripSpendingSummaryResponse.SpendingItem.builder()
                        .spendingId(spending.getId())
                        .spendingName(spending.getName())
                        .tripSpendingType(spending.getTripSpendingType() == null ? TripSpendingType.OTHER : spending.getTripSpendingType())
                        .amount(spending.getAmount() == null ? 0 : spending.getAmount())
                        .paidBy(toUserItem(spending.getUser()))
                        .includedUsers(spending.getIncludedUsers().stream()
                                .map(this::toUserItem)
                                .sorted(Comparator.comparing(
                                        TripSpendingSummaryResponse.UserItem::getUsername,
                                        Comparator.nullsLast(String::compareToIgnoreCase)
                                ))
                                .toList())
                        .build())
                .sorted(Comparator.comparing(
                        TripSpendingSummaryResponse.SpendingItem::getSpendingName,
                        Comparator.nullsLast(String::compareToIgnoreCase)
                ))
                .toList();
    }

    private TripSpendingSummaryResponse.UserItem toUserItem(User user) {
        if (user == null) {
            return null;
        }
        return TripSpendingSummaryResponse.UserItem.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    private StageKey toStageKey(TripStage tripStage) {
        return new StageKey(tripStage.getId(), tripStage.getName(), tripStage.getStartTime(), tripStage.getEndTime());
    }

    private record StageKey(Long id, String name, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime) {
    }
}
