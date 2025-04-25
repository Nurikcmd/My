package com.chess.tournament.repository;

import com.chess.tournament.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    // Дополнительные методы поиска можно добавить здесь
} 