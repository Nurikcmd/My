package com.chess.tournament.controller;

import com.chess.tournament.model.TournamentRegistration;
import com.chess.tournament.service.TournamentRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class TournamentRegistrationController {
    private final TournamentRegistrationService registrationService;

    @Autowired
    public TournamentRegistrationController(TournamentRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public List<TournamentRegistration> getAllRegistrations() {
        return registrationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentRegistration> getRegistrationById(@PathVariable Long id) {
        return registrationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public TournamentRegistration registerPlayer(@RequestParam Long playerId, @RequestParam Long tournamentId) {
        return registrationService.registerPlayer(playerId, tournamentId);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<TournamentRegistration> approveRegistration(@PathVariable Long id) {
        try {
            TournamentRegistration registration = registrationService.approveRegistration(id);
            return ResponseEntity.ok(registration);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<TournamentRegistration> markAsPaid(@PathVariable Long id) {
        try {
            TournamentRegistration registration = registrationService.markAsPaid(id);
            return ResponseEntity.ok(registration);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<TournamentRegistration> cancelRegistration(@PathVariable Long id) {
        try {
            TournamentRegistration registration = registrationService.cancelRegistration(id);
            return ResponseEntity.ok(registration);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 