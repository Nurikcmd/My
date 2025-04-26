package com.chess.tournament.controller;

import com.chess.tournament.model.Tournament;
import com.chess.tournament.model.TournamentResult;
import com.chess.tournament.service.TournamentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournament-management")
public class TournamentManagementController {

    private final TournamentManagementService tournamentManagementService;

    @Autowired
    public TournamentManagementController(TournamentManagementService tournamentManagementService) {
        this.tournamentManagementService = tournamentManagementService;
    }

    @GetMapping("/tournaments")
    public List<Tournament> getAllTournaments() {
        return tournamentManagementService.getAllTournaments();
    }

    @GetMapping("/tournaments/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentManagementService.getTournamentById(id));
    }

    @PostMapping("/tournaments")
    public ResponseEntity<Tournament> createTournament(@RequestBody Tournament tournament) {
        return ResponseEntity.ok(tournamentManagementService.createTournament(tournament));
    }

    @PutMapping("/tournaments/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long id, @RequestBody Tournament tournament) {
        return ResponseEntity.ok(tournamentManagementService.updateTournament(id, tournament));
    }

    @DeleteMapping("/tournaments/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentManagementService.deleteTournament(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tournaments/{tournamentId}/results")
    public List<TournamentResult> getTournamentResults(@PathVariable Long tournamentId) {
        return tournamentManagementService.getTournamentResults(tournamentId);
    }

    @GetMapping("/tournaments/{tournamentId}/players/{playerId}/result")
    public ResponseEntity<TournamentResult> getPlayerResult(@PathVariable Long tournamentId, @PathVariable Long playerId) {
        return tournamentManagementService.getPlayerResult(tournamentId, playerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/tournaments/{tournamentId}/calculate-results")
    public ResponseEntity<Void> calculateTournamentResults(@PathVariable Long tournamentId) {
        tournamentManagementService.calculateTournamentResults(tournamentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tournaments/results")
    public ResponseEntity<TournamentResult> createResult(@RequestBody TournamentResult result) {
        return ResponseEntity.ok(tournamentManagementService.createResult(result));
    }

    @PutMapping("/tournaments/results")
    public ResponseEntity<TournamentResult> updateResult(@RequestBody TournamentResult result) {
        return ResponseEntity.ok(tournamentManagementService.updateResult(result));
    }

    @DeleteMapping("/tournaments/results/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        tournamentManagementService.deleteResult(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tournaments/{tournamentId}/standings")
    public List<TournamentResult> getTournamentStandings(@PathVariable Long tournamentId) {
        return tournamentManagementService.getTournamentStandings(tournamentId);
    }
} 