package com.chess.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    @ManyToOne
    @JoinColumn(name = "white_player_id", nullable = false)
    private Player whitePlayer;

    @ManyToOne
    @JoinColumn(name = "black_player_id", nullable = false)
    private Player blackPlayer;

    @Column(nullable = false)
    private LocalDateTime scheduledTime;

    private String result;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    public enum MatchStatus {
        SCHEDULED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
} 