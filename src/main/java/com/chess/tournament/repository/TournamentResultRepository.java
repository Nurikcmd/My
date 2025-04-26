package com.chess.tournament.repository;

import com.chess.tournament.model.TournamentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentResultRepository extends JpaRepository<TournamentResult, Long> {
    List<TournamentResult> findByTournamentId(Long tournamentId);
    Optional<TournamentResult> findByTournamentIdAndPlayerId(Long tournamentId, Long playerId);
    @Query("SELECT tr FROM TournamentResult tr WHERE tr.tournament.id = :tournamentId ORDER BY tr.points DESC, tr.tiebreakScore DESC")
    List<TournamentResult> findTournamentStandings(Long tournamentId);
} 