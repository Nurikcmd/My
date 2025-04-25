package com.chess.tournament.service;

import com.chess.tournament.model.Organizer;
import com.chess.tournament.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizerService {
    private final OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    public List<Organizer> findAll() {
        return organizerRepository.findAll();
    }

    public Optional<Organizer> findById(Long id) {
        return organizerRepository.findById(id);
    }

    public Organizer save(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    public Organizer update(Long id, Organizer organizer) {
        if (!organizerRepository.existsById(id)) {
            throw new RuntimeException("Organizer not found with id: " + id);
        }
        organizer.setId(id);
        return organizerRepository.save(organizer);
    }

    public void deleteById(Long id) {
        organizerRepository.deleteById(id);
    }
} 