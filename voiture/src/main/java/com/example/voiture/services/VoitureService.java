package com.example.voiture.services;

import com.example.voiture.entities.Voiture;
import com.example.voiture.repositories.VoitureRepository;
import org.springframework.stereotype.Service;

@Service
public class VoitureService {

    private final VoitureRepository voitureRepository;

    public VoitureService(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    public Voiture enregistrerVoiture(Voiture v) {
        return voitureRepository.save(v);
    }
}
