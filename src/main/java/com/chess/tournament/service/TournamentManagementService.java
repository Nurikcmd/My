package com.chess.tournament.service;

import com.chess.tournament.model.*;
import com.chess.tournament.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TournamentManagementService {
    private final TournamentRepository tournamentRepository;
    private final TournamentResultRepository tournamentResultRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public TournamentManagementService(TournamentRepository tournamentRepository,
                                     TournamentResultRepository tournamentResultRepository,
                                     PlayerRepository playerRepository,
                                     GameRepository gameRepository) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentResultRepository = tournamentResultRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    // Tournament methods
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Tournament getTournamentById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found"));
    }

    @Transactional
    public Tournament createTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Transactional
    public Tournament updateTournament(Long id, Tournament tournament) {
        if (!tournamentRepository.existsById(id)) {
            throw new EntityNotFoundException("Tournament not found");
        }
        tournament.setId(id);
        return tournamentRepository.save(tournament);
    }

    @Transactional
    public void deleteTournament(Long id) {
        if (!tournamentRepository.existsById(id)) {
            throw new EntityNotFoundException("Tournament not found");
        }
        tournamentRepository.deleteById(id);
    }

    // Tournament Result methods
    public List<TournamentResult> getTournamentResults(Long tournamentId) {
        return tournamentResultRepository.findByTournamentId(tournamentId);
    }

    public Optional<TournamentResult> getPlayerResult(Long tournamentId, Long playerId) {
        return tournamentResultRepository.findByTournamentIdAndPlayerId(tournamentId, playerId);
    }

    public void updateTournamentResults(Long tournamentId) {
        List<Game> games = gameRepository.findByTournamentId(tournamentId);
        Map<Player, TournamentResult> results = tournamentResultRepository.findByTournamentId(tournamentId)
                .stream()
                .collect(Collectors.toMap(TournamentResult::getPlayer, result -> result));

        for (Game game : games) {
            Player whitePlayer = game.getWhitePlayer();
            Player blackPlayer = game.getBlackPlayer();

            TournamentResult whiteResult = results.computeIfAbsent(whitePlayer, 
                player -> createNewResult(tournamentId, player));
            TournamentResult blackResult = results.computeIfAbsent(blackPlayer, 
                player -> createNewResult(tournamentId, player));

            updateResults(game, whiteResult, blackResult);
        }

        tournamentResultRepository.saveAll(results.values());
    }

    private TournamentResult createNewResult(Long tournamentId, Player player) {
        TournamentResult result = new TournamentResult();
        result.setTournament(new Tournament());
        result.getTournament().setId(tournamentId);
        result.setPlayer(player);
        result.setPoints(0.0);
        result.setGamesPlayed(0);
        result.setWins(0);
        result.setDraws(0);
        result.setLosses(0);
        result.setTiebreakScore(0.0);
        return result;
    }

    private void updateResults(Game game, TournamentResult whiteResult, TournamentResult blackResult) {
        whiteResult.setGamesPlayed(whiteResult.getGamesPlayed() + 1);
        blackResult.setGamesPlayed(blackResult.getGamesPlayed() + 1);

        if (game.getResult() == GameResult.WHITE_WIN) {
            whiteResult.setPoints(whiteResult.getPoints() + 1.0);
            whiteResult.setWins(whiteResult.getWins() + 1);
            blackResult.setLosses(blackResult.getLosses() + 1);
        } else if (game.getResult() == GameResult.BLACK_WIN) {
            blackResult.setPoints(blackResult.getPoints() + 1.0);
            blackResult.setWins(blackResult.getWins() + 1);
            whiteResult.setLosses(whiteResult.getLosses() + 1);
        } else if (game.getResult() == GameResult.DRAW) {
            whiteResult.setPoints(whiteResult.getPoints() + 0.5);
            blackResult.setPoints(blackResult.getPoints() + 0.5);
            whiteResult.setDraws(whiteResult.getDraws() + 1);
            blackResult.setDraws(blackResult.getDraws() + 1);
        }
    }

    @Transactional
    public TournamentResult createResult(TournamentResult result) {
        return tournamentResultRepository.save(result);
    }

    @Transactional
    public TournamentResult updateResult(TournamentResult result) {
        return tournamentResultRepository.save(result);
    }

    @Transactional
    public void deleteResult(Long id) {
        tournamentResultRepository.deleteById(id);
    }

    public List<TournamentResult> getTournamentStandings(Long tournamentId) {
        return tournamentResultRepository.findTournamentStandings(tournamentId);
    }

    @Transactional
    public void calculateTournamentResults(Long tournamentId) {
        // Получаем все игры турнира
        List<Game> games = gameRepository.findByTournamentId(tournamentId);
        
        // Получаем или создаем результаты для всех игроков
        Map<Player, TournamentResult> results = tournamentResultRepository.findByTournamentId(tournamentId)
                .stream()
                .collect(Collectors.toMap(TournamentResult::getPlayer, result -> result));

        // Обновляем результаты на основе игр
        for (Game game : games) {
            Player whitePlayer = game.getWhitePlayer();
            Player blackPlayer = game.getBlackPlayer();

            TournamentResult whiteResult = results.computeIfAbsent(whitePlayer, 
                player -> createNewResult(tournamentId, player));
            TournamentResult blackResult = results.computeIfAbsent(blackPlayer, 
                player -> createNewResult(tournamentId, player));

            updateResults(game, whiteResult, blackResult);
        }

        // Сохраняем обновленные результаты
        tournamentResultRepository.saveAll(results.values());
    }
} 