package com.chess.tournament.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tournament_registrations")
public class TournamentRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false)
    private Boolean isApproved;

    private String paymentStatus;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    public enum RegistrationStatus {
        PENDING,
        APPROVED,
        REJECTED,
        CANCELLED
    }
} 