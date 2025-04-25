package com.chess.tournament.service;

import com.chess.tournament.model.Tournament;
import com.chess.tournament.model.TournamentFormat;
import com.chess.tournament.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TournamentService {
    
    private final TournamentRepository tournamentRepository;
    private final OrganizerService organizerService;
    private final VenueService venueService;

    @Autowired
    public TournamentService(
            TournamentRepository tournamentRepository,
            OrganizerService organizerService,
            VenueService venueService) {
        this.tournamentRepository = tournamentRepository;
        this.organizerService = organizerService;
        this.venueService = venueService;
    }

    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public Optional<Tournament> findById(Long id) {
        return tournamentRepository.findById(id);
    }

    public Tournament save(Tournament tournament) {
        validateTournament(tournament);
        return tournamentRepository.save(tournament);
    }

    public void deleteById(Long id) {
        if (!tournamentRepository.existsById(id)) {
            throw new RuntimeException("Tournament not found with id: " + id);
        }
        tournamentRepository.deleteById(id);
    }

    public Tournament update(Long id, Tournament tournament) {
        if (!tournamentRepository.existsById(id)) {
            throw new RuntimeException("Tournament not found with id: " + id);
        }
        tournament.setId(id);
        validateTournament(tournament);
        return tournamentRepository.save(tournament);
    }

    private void validateTournament(Tournament tournament) {
        if (tournament.getEndDate().isBefore(tournament.getStartDate())) {
            throw new RuntimeException("End date cannot be before start date");
        }

        if (tournament.getRounds() != null && tournament.getRounds() <= 0) {
            throw new RuntimeException("Number of rounds must be positive");
        }

        if (tournament.getName() == null || tournament.getName().trim().isEmpty()) {
            throw new RuntimeException("Tournament name is required");
        }

        if (tournament.getFormat() == null) {
            throw new RuntimeException("Tournament format is required");
        }

        if (tournament.getOrganizer() == null || tournament.getOrganizer().getId() == null) {
            throw new RuntimeException("Organizer is required");
        }

        if (!organizerService.findById(tournament.getOrganizer().getId()).isPresent()) {
            throw new RuntimeException("Organizer not found with id: " + tournament.getOrganizer().getId());
        }

        if (tournament.getVenue() == null || tournament.getVenue().getId() == null) {
            throw new RuntimeException("Venue is required");
        }

        if (!venueService.findById(tournament.getVenue().getId()).isPresent()) {
            throw new RuntimeException("Venue not found with id: " + tournament.getVenue().getId());
        }

        // Check for date conflicts
        List<Tournament> existingTournaments = tournamentRepository.findByVenueAndDateRange(
            tournament.getVenue().getId(),
            tournament.getStartDate(),
            tournament.getEndDate()
        );

        if (!existingTournaments.isEmpty() && 
            (tournament.getId() == null || 
             existingTournaments.stream().anyMatch(t -> !t.getId().equals(tournament.getId())))) {
            throw new RuntimeException("Venue is already booked for the specified date range");
        }
    }
} 