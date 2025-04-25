package com.chess.tournament.service;

import com.chess.tournament.model.Venue;
import com.chess.tournament.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    private final VenueRepository venueRepository;

    @Autowired
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    public Optional<Venue> findById(Long id) {
        return venueRepository.findById(id);
    }

    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }

    public Venue update(Long id, Venue venue) {
        if (!venueRepository.existsById(id)) {
            throw new RuntimeException("Venue not found with id: " + id);
        }
        venue.setId(id);
        return venueRepository.save(venue);
    }

    public void deleteById(Long id) {
        venueRepository.deleteById(id);
    }
} 