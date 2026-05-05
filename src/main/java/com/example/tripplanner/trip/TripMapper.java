package com.example.tripplanner.trip;

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
public class TripMapper {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public Trip toEntity(TripRequest request) {
        Trip trip = modelMapper.map(request, Trip.class);
        trip.setUsers(resolveUsers(request.getUserIds()));
        return trip;
    }

    public void updateEntity(TripRequest request, Trip trip) {
        modelMapper.map(request, trip);
        trip.setUsers(resolveUsers(request.getUserIds()));
    }

    private Set<User> resolveUsers(Set<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return new HashSet<>();
        }
        List<User> users = userRepository.findAllById(userIds);
        if (users.size() != userIds.size()) {
            throw new EntityNotFoundException("One or more users were not found");
        }
        return new HashSet<>(users);
    }
}
