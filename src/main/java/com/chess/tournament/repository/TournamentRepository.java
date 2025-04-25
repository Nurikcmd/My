package com.chess.tournament.repository;

import com.chess.tournament.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    // Дополнительные методы поиска можно добавить здесь

    @Query("SELECT t FROM Tournament t WHERE t.venue.id = :venueId " +
           "AND ((t.startDate BETWEEN :startDate AND :endDate) " +
           "OR (t.endDate BETWEEN :startDate AND :endDate) " +
           "OR (t.startDate <= :startDate AND t.endDate >= :endDate))")
    List<Tournament> findByVenueAndDateRange(@Param("venueId") Long venueId,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);
} 