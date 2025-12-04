package com.example.voiture.repositories;

import com.example.voiture.entities.Voiture;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    List<Voiture> findByClientId(Long clientId);}
