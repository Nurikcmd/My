package com.chess.tournament.service;

import com.chess.tournament.model.Tournament;
import com.chess.tournament.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TournamentService {
    
    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public Optional<Tournament> findById(Long id) {
        return tournamentRepository.findById(id);
    }

    public Tournament save(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public void deleteById(Long id) {
        tournamentRepository.deleteById(id);
    }

    public Tournament update(Long id, Tournament tournament) {
        if (tournamentRepository.existsById(id)) {
            tournament.setId(id);
            return tournamentRepository.save(tournament);
        }
        throw new RuntimeException("Tournament not found with id: " + id);
    }
} 