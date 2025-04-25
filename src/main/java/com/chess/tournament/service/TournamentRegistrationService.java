package com.chess.tournament.service;

import com.chess.tournament.model.TournamentRegistration;
import com.chess.tournament.repository.TournamentRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TournamentRegistrationService {
    private final TournamentRegistrationRepository registrationRepository;
    private final PlayerService playerService;
    private final TournamentService tournamentService;

    @Autowired
    public TournamentRegistrationService(TournamentRegistrationRepository registrationRepository,
                                       PlayerService playerService,
                                       TournamentService tournamentService) {
        this.registrationRepository = registrationRepository;
        this.playerService = playerService;
        this.tournamentService = tournamentService;
    }

    public List<TournamentRegistration> findAll() {
        return registrationRepository.findAll();
    }

    public Optional<TournamentRegistration> findById(Long id) {
        return registrationRepository.findById(id);
    }

    public TournamentRegistration registerPlayer(Long playerId, Long tournamentId) {
        if (!playerService.findById(playerId).isPresent()) {
            throw new RuntimeException("Player not found with id: " + playerId);
        }

        if (!tournamentService.findById(tournamentId).isPresent()) {
            throw new RuntimeException("Tournament not found with id: " + tournamentId);
        }

        TournamentRegistration registration = new TournamentRegistration();
        registration.setPlayer(playerService.findById(playerId).get());
        registration.setTournament(tournamentService.findById(tournamentId).get());
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus(TournamentRegistration.RegistrationStatus.PENDING);
        registration.setPaymentStatus(TournamentRegistration.PaymentStatus.PENDING);

        return registrationRepository.save(registration);
    }

    public TournamentRegistration approveRegistration(Long id) {
        TournamentRegistration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with id: " + id));

        registration.setStatus(TournamentRegistration.RegistrationStatus.APPROVED);
        return registrationRepository.save(registration);
    }

    public TournamentRegistration markAsPaid(Long id) {
        TournamentRegistration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with id: " + id));

        registration.setPaymentStatus(TournamentRegistration.PaymentStatus.PAID);
        return registrationRepository.save(registration);
    }

    public TournamentRegistration cancelRegistration(Long id) {
        TournamentRegistration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found with id: " + id));

        registration.setStatus(TournamentRegistration.RegistrationStatus.CANCELLED);
        return registrationRepository.save(registration);
    }

    public void deleteById(Long id) {
        registrationRepository.deleteById(id);
    }
} 