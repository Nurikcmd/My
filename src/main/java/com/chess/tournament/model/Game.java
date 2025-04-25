package com.chess.tournament.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @Column(nullable = false)
    private Integer roundNumber;

    @ManyToOne
    @JoinColumn(name = "player_white_id", nullable = false)
    private Player playerWhite;

    @ManyToOne
    @JoinColumn(name = "player_black_id", nullable = false)
    private Player playerBlack;

    @Column(nullable = false)
    private Integer result; // 1 - победа белых, 0 - ничья, -1 - победа черных
} 