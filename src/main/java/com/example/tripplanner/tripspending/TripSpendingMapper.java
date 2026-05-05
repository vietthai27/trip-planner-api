package com.example.tripplanner.tripspending;

import com.example.tripplanner.trip.Trip;
import com.example.tripplanner.trip.TripRepository;
import com.example.tripplanner.tripstage.TripStage;
import com.example.tripplanner.tripstage.TripStageRepository;
import com.example.tripplanner.user.User;
import com.example.tripplanner.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TripSpendingMapper {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final TripStageRepository tripStageRepository;

    public TripSpending toEntity(TripSpendingRequest request) {
        TripSpending tripSpending = modelMapper.map(request, TripSpending.class);
        applyRelationships(request, tripSpending);
        return tripSpending;
    }

    public void updateEntity(TripSpendingRequest request, TripSpending tripSpending) {
        modelMapper.map(request, tripSpending);
        applyRelationships(request, tripSpending);
    }

    private void applyRelationships(TripSpendingRequest request, TripSpending tripSpending) {
        tripSpending.setUser(resolveUser(request.getUserId()));
        tripSpending.setTrip(resolveTrip(request.getTripId()));
        tripSpending.setTripStage(resolveTripStage(request.getTripStageId()));
        tripSpending.setIncludedUsers(resolveIncludedUsers(request.getIncludedUserIds()));
    }

    private User resolveUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
    }

    private Trip resolveTrip(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found: " + tripId));
    }

    private TripStage resolveTripStage(Long tripStageId) {
        if (tripStageId == null) {
            return null;
        }
        return tripStageRepository.findById(tripStageId)
                .orElseThrow(() -> new EntityNotFoundException("Trip stage not found: " + tripStageId));
    }

    private Set<User> resolveIncludedUsers(Set<Long> includedUserIds) {
        if (includedUserIds == null || includedUserIds.isEmpty()) {
            return new HashSet<>();
        }
        List<User> users = userRepository.findAllById(includedUserIds);
        if (users.size() != includedUserIds.size()) {
            throw new EntityNotFoundException("One or more included users were not found");
        }
        return new HashSet<>(users);
    }
}
