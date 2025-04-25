package com.chess.tournament.repository;

import com.chess.tournament.model.TournamentRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRegistrationRepository extends JpaRepository<TournamentRegistration, Long> {
    List<TournamentRegistration> findByTournamentId(Long tournamentId);
    List<TournamentRegistration> findByPlayerId(Long playerId);
    Optional<TournamentRegistration> findByPlayerIdAndTournamentId(Long playerId, Long tournamentId);
} 