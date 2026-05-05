package com.example.tripplanner.tripstage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripStageRepository extends JpaRepository<TripStage, Long> {
    List<TripStage> findByTrip_Id(Long tripId);
}
