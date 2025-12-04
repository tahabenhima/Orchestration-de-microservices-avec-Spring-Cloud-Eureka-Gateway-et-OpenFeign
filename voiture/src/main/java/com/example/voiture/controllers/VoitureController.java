package com.example.voiture.controllers;

import com.example.voiture.entities.Client;
import com.example.voiture.entities.Voiture;
import com.example.voiture.repositories.VoitureRepository;
import com.example.voiture.services.ClientService;
import com.example.voiture.services.VoitureService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.List;

@RestController
public class VoitureController {

    private final VoitureRepository voitureRepository;
    private final VoitureService voitureService;
    private final ClientService clientService;

    public VoitureController(VoitureRepository voitureRepository,
                             VoitureService voitureService,
                             ClientService clientService) {
        this.voitureRepository = voitureRepository;
        this.voitureService = voitureService;
        this.clientService = clientService;
    }

    @GetMapping("/voitures")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(voitureRepository.findAll());
    }

    @GetMapping("/voitures/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Voiture v = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Not found"));
            v.setClient(clientService.clientById(v.getClientId()));
            return ResponseEntity.ok(v);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Voiture not found");
        }
    }

    @GetMapping("/voitures/client/{id}")
    public ResponseEntity<?> findByClient(@PathVariable Long id) {
        return ResponseEntity.ok(voitureRepository.findByClientId(id));
    }

    @PostMapping("/voitures/{clientId}")
    public ResponseEntity<?> save(@PathVariable Long clientId,
                                  @RequestBody Voiture voiture) {
        try {
            Client c = clientService.clientById(clientId);
            voiture.setClientId(clientId);
            voiture.setClient(c);
            return ResponseEntity.ok(voitureService.enregistrerVoiture(voiture));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Client not found");
        }
    }

    @PutMapping("/voitures/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Voiture updated) {
        try {
            Voiture v = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Not found"));

            if (updated.getMatricule() != null) v.setMatricule(updated.getMatricule());
            if (updated.getMarque() != null) v.setMarque(updated.getMarque());
            if (updated.getModel() != null) v.setModel(updated.getModel());

            return ResponseEntity.ok(voitureRepository.save(v));

        } catch (Exception e) {
            return ResponseEntity.status(404).body("Voiture not found");
        }
    }
}
