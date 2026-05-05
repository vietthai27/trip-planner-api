package com.example.tripplanner.tripspending;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripSpendingRepository extends JpaRepository<TripSpending, Long> {
    List<TripSpending> findByTripId(Long tripId);
}
