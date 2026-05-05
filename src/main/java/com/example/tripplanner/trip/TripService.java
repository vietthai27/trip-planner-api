package com.example.tripplanner.trip;

import com.example.tripplanner.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public List<Trip> findAllByUserId(Long userId) {
        return tripRepository.findDistinctByUsers_Id(userId);
    }

    public Trip findById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<User> findUsersByTripId(Long tripId) {
        return findById(tripId).getUsers().stream()
                .sorted(Comparator.comparing(User::getUsername, Comparator.nullsLast(String::compareToIgnoreCase)))
                .toList();
    }

    @Transactional
    public Trip create(TripRequest request) {
        return tripRepository.save(tripMapper.toEntity(request));
    }

    @Transactional
    public Trip update(Long id, TripRequest request) {
        Trip trip = findById(id);
        tripMapper.updateEntity(request, trip);
        return tripRepository.save(trip);
    }

    public void delete(Long id) {
        if (!tripRepository.existsById(id)) {
            throw new EntityNotFoundException("Trip not found: " + id);
        }
        tripRepository.deleteById(id);
    }
}
