package com.chess.tournament.repository;

import com.chess.tournament.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByTournamentId(Long tournamentId);
    List<Game> findByTournamentIdAndRoundNumber(Long tournamentId, Integer roundNumber);
    List<Game> findByTournamentIdAndWhitePlayerIdOrBlackPlayerId(Long tournamentId, Long whitePlayerId, Long blackPlayerId);
    boolean existsByTournamentIdAndRoundNumberAndBoardNumber(Long tournamentId, Integer roundNumber, Integer boardNumber);
} 