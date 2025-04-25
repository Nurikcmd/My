package com.chess.tournament.repository;

import com.chess.tournament.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    // Дополнительные методы поиска можно добавить здесь
} 