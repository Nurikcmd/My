package com.chess.tournament.controller;

import com.chess.tournament.model.Game;
import com.chess.tournament.model.GameResult;
import com.chess.tournament.model.Round;
import com.chess.tournament.service.TournamentGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tournament-games")
public class TournamentGameController {
    private final TournamentGameService tournamentGameService;

    public TournamentGameController(TournamentGameService tournamentGameService) {
        this.tournamentGameService = tournamentGameService;
    }

    // Game endpoints
    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<Game>> getTournamentGames(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(tournamentGameService.getTournamentGames(tournamentId));
    }

    @GetMapping("/tournament/{tournamentId}/round/{roundNumber}/games")
    public ResponseEntity<List<Game>> getRoundGames(
            @PathVariable Long tournamentId,
            @PathVariable Integer roundNumber) {
        return ResponseEntity.ok(tournamentGameService.getRoundGames(tournamentId, roundNumber));
    }

    @GetMapping("/tournament/{tournamentId}/player/{playerId}")
    public ResponseEntity<List<Game>> getPlayerGames(
            @PathVariable Long tournamentId,
            @PathVariable Long playerId) {
        return ResponseEntity.ok(tournamentGameService.getPlayerGames(tournamentId, playerId));
    }

    @PostMapping("/game")
    public ResponseEntity<Game> createGame(
            @RequestParam Long tournamentId,
            @RequestParam Long whitePlayerId,
            @RequestParam Long blackPlayerId,
            @RequestParam Integer roundNumber,
            @RequestParam Integer boardNumber,
            @RequestParam LocalDateTime gameDate) {
        return ResponseEntity.ok(tournamentGameService.createGame(
                tournamentId, whitePlayerId, blackPlayerId, roundNumber, boardNumber, gameDate));
    }

    @PutMapping("/game/{gameId}/result")
    public ResponseEntity<Game> updateGameResult(
            @PathVariable Long gameId,
            @RequestParam GameResult result,
            @RequestParam(required = false) String pgnNotation) {
        return ResponseEntity.ok(tournamentGameService.updateGameResult(gameId, result, pgnNotation));
    }

    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
        tournamentGameService.deleteGame(gameId);
        return ResponseEntity.ok().build();
    }

    // Round endpoints
    @GetMapping("/tournament/{tournamentId}/rounds")
    public ResponseEntity<List<Round>> getTournamentRounds(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(tournamentGameService.getTournamentRounds(tournamentId));
    }

    @GetMapping("/tournament/{tournamentId}/round/{roundNumber}")
    public ResponseEntity<Round> getRound(
            @PathVariable Long tournamentId,
            @PathVariable Integer roundNumber) {
        return ResponseEntity.ok(tournamentGameService.getRound(tournamentId, roundNumber));
    }

    @PostMapping("/round")
    public ResponseEntity<Round> createRound(
            @RequestParam Long tournamentId,
            @RequestParam Integer roundNumber,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        return ResponseEntity.ok(tournamentGameService.createRound(
                tournamentId, roundNumber, startTime, endTime));
    }

    @PutMapping("/tournament/{tournamentId}/round/{roundNumber}")
    public ResponseEntity<Round> updateRound(
            @PathVariable Long tournamentId,
            @PathVariable Integer roundNumber,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        return ResponseEntity.ok(tournamentGameService.updateRound(
                tournamentId, roundNumber, startTime, endTime));
    }

    @DeleteMapping("/tournament/{tournamentId}/round/{roundNumber}")
    public ResponseEntity<Void> deleteRound(
            @PathVariable Long tournamentId,
            @PathVariable Integer roundNumber) {
        tournamentGameService.deleteRound(tournamentId, roundNumber);
        return ResponseEntity.ok().build();
    }
} 