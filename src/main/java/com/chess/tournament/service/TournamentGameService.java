package com.chess.tournament.service;

import com.chess.tournament.model.*;
import com.chess.tournament.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentGameService {
    private final GameRepository gameRepository;
    private final RoundRepository roundRepository;
    private final TournamentRepository tournamentRepository;
    private final PlayerRepository playerRepository;

    public TournamentGameService(GameRepository gameRepository,
                               RoundRepository roundRepository,
                               TournamentRepository tournamentRepository,
                               PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
        this.tournamentRepository = tournamentRepository;
        this.playerRepository = playerRepository;
    }

    // Game methods
    public List<Game> getTournamentGames(Long tournamentId) {
        return gameRepository.findByTournamentId(tournamentId);
    }

    public List<Game> getRoundGames(Long tournamentId, Integer roundNumber) {
        return gameRepository.findByTournamentIdAndRoundNumber(tournamentId, roundNumber);
    }

    public List<Game> getPlayerGames(Long tournamentId, Long playerId) {
        return gameRepository.findByTournamentIdAndWhitePlayerIdOrBlackPlayerId(tournamentId, playerId, playerId);
    }

    @Transactional
    public Game createGame(Long tournamentId, Long whitePlayerId, Long blackPlayerId,
                         Integer roundNumber, Integer boardNumber, LocalDateTime gameDate) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found"));
        
        Player whitePlayer = playerRepository.findById(whitePlayerId)
                .orElseThrow(() -> new EntityNotFoundException("White player not found"));
        
        Player blackPlayer = playerRepository.findById(blackPlayerId)
                .orElseThrow(() -> new EntityNotFoundException("Black player not found"));

        if (gameRepository.existsByTournamentIdAndRoundNumberAndBoardNumber(tournamentId, roundNumber, boardNumber)) {
            throw new IllegalStateException("Game with this board number already exists in this round");
        }

        Game game = new Game();
        game.setTournament(tournament);
        game.setWhitePlayer(whitePlayer);
        game.setBlackPlayer(blackPlayer);
        game.setRoundNumber(roundNumber);
        game.setBoardNumber(boardNumber);
        game.setGameDate(gameDate);
        game.setResult(GameResult.NOT_PLAYED);

        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGameResult(Long gameId, GameResult result, String pgnNotation) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        game.setResult(result);
        game.setPgnNotation(pgnNotation);
        
        return gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(Long gameId) {
        if (!gameRepository.existsById(gameId)) {
            throw new EntityNotFoundException("Game not found");
        }
        gameRepository.deleteById(gameId);
    }

    // Round methods
    public List<Round> getTournamentRounds(Long tournamentId) {
        return roundRepository.findByTournamentId(tournamentId);
    }

    public Round getRound(Long tournamentId, Integer roundNumber) {
        return roundRepository.findByTournamentIdAndRoundNumber(tournamentId, roundNumber)
                .orElseThrow(() -> new EntityNotFoundException("Round not found"));
    }

    @Transactional
    public Round createRound(Long tournamentId, Integer roundNumber, LocalDateTime startTime, LocalDateTime endTime) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found"));

        if (roundRepository.existsByTournamentIdAndRoundNumber(tournamentId, roundNumber)) {
            throw new IllegalStateException("Round with this number already exists in the tournament");
        }

        Round round = new Round();
        round.setTournament(tournament);
        round.setRoundNumber(roundNumber);
        round.setStartTime(startTime);
        round.setEndTime(endTime);

        return roundRepository.save(round);
    }

    @Transactional
    public Round updateRound(Long tournamentId, Integer roundNumber, LocalDateTime startTime, LocalDateTime endTime) {
        Round round = roundRepository.findByTournamentIdAndRoundNumber(tournamentId, roundNumber)
                .orElseThrow(() -> new EntityNotFoundException("Round not found"));

        round.setStartTime(startTime);
        round.setEndTime(endTime);

        return roundRepository.save(round);
    }

    @Transactional
    public void deleteRound(Long tournamentId, Integer roundNumber) {
        Round round = roundRepository.findByTournamentIdAndRoundNumber(tournamentId, roundNumber)
                .orElseThrow(() -> new EntityNotFoundException("Round not found"));
        roundRepository.delete(round);
    }
} 