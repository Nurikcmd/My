package com.chess.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "match_results")
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @Column(nullable = false)
    private LocalDateTime completionTime;

    @Column(nullable = false)
    private Integer movesPlayed;

    private String opening;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    private String pgnNotation;

    public enum ResultType {
        WHITE_WIN,
        BLACK_WIN,
        DRAW,
        BYE,
        FORFEIT
    }
} 