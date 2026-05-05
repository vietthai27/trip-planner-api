package com.example.tripplanner.tripstage;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripStageService {

    private final TripStageRepository tripStageRepository;
    private final TripStageMapper tripStageMapper;

    public List<TripStage> findAll() {
        return tripStageRepository.findAll();
    }

    public List<TripStage> findAllByTripId(Long tripId) {
        return tripStageRepository.findByTrip_Id(tripId);
    }

    public TripStage findById(Long id) {
        return tripStageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip stage not found: " + id));
    }

    public TripStage create(TripStageRequest request) {
        return tripStageRepository.save(tripStageMapper.toEntity(request));
    }

    public TripStage update(Long id, TripStageRequest request) {
        TripStage tripStage = findById(id);
        tripStageMapper.updateEntity(request, tripStage);
        return tripStageRepository.save(tripStage);
    }

    public void delete(Long id) {
        if (!tripStageRepository.existsById(id)) {
            throw new EntityNotFoundException("Trip stage not found: " + id);
        }
        tripStageRepository.deleteById(id);
    }
}
