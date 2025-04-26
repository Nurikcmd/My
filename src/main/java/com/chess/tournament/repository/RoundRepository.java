package com.chess.tournament.repository;

import com.chess.tournament.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findByTournamentId(Long tournamentId);
    Optional<Round> findByTournamentIdAndRoundNumber(Long tournamentId, Integer roundNumber);
    boolean existsByTournamentIdAndRoundNumber(Long tournamentId, Integer roundNumber);
} 