package com.example.tripplanner.tripstage;

import com.example.tripplanner.trip.Trip;
import com.example.tripplanner.trip.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TripStageMapper {

    private final ModelMapper modelMapper;
    private final TripRepository tripRepository;

    public TripStage toEntity(TripStageRequest request) {
        TripStage tripStage = modelMapper.map(request, TripStage.class);
        tripStage.setTrip(resolveTrip(request.getTripId()));
        return tripStage;
    }

    public void updateEntity(TripStageRequest request, TripStage tripStage) {
        modelMapper.map(request, tripStage);
        tripStage.setTrip(resolveTrip(request.getTripId()));
    }

    private Trip resolveTrip(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found: " + tripId));
    }
}
