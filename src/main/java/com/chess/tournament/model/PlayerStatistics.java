package com.chess.tournament.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "player_statistics")
public class PlayerStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(nullable = false)
    private Integer totalTournaments;

    @Column(nullable = false)
    private Integer tournamentsWon;

    @Column(nullable = false)
    private Integer totalGames;

    @Column(nullable = false)
    private Integer gamesWon;

    @Column(nullable = false)
    private Integer gamesDrawn;

    @Column(nullable = false)
    private Integer gamesLost;

    @Column(nullable = false)
    private Double winPercentage;

    @Column(nullable = false)
    private Integer highestRating;

    @Column(nullable = false)
    private Integer currentRating;
} 