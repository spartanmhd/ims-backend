package com.example.ims_backend.service;

import com.example.ims_backend.entity.Origine;
import com.example.ims_backend.repository.OrigineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrigineService {

    @Autowired
    private OrigineRepository origineRepository;

    // Create a new origine (no quantite, no prixAchat)
    public Origine saveOrigine(Origine origine) {
        return origineRepository.save(origine);
    }

    // Update an existing origine (no quantite, no prixAchat)
    public Optional<Origine> updateOrigine(Integer id, Origine updatedOrigine) {
        return origineRepository.findById(id).map(existingOrigine -> {
            existingOrigine.setNom(updatedOrigine.getNom());
            existingOrigine.setFournisseur(updatedOrigine.getFournisseur());
            // Do not update quantite or prixAchat!
            return origineRepository.save(existingOrigine);
        });
    }

    // Get all origines
    public Page<Origine> getAllOrigines(Pageable pageable) {
        return origineRepository.findAll(pageable);
    }

    // Get a single origine by id
    public Optional<Origine> getOrigine(Integer id) {
        return origineRepository.findById(id);
    }

    // Delete an origine by id
    public boolean deleteOrigine(Integer id) {
        return origineRepository.findById(id).map(o -> {
            origineRepository.delete(o);
            return true;
        }).orElse(false);
    }

    // Search origines by nom (name)
    public Page<Origine> searchByNom(String nom, Pageable pageable) {
        return origineRepository.findByNomContainingIgnoreCase(nom, pageable);
    }

    // Search origines by fournisseur ICE
    public Page<Origine> searchByFournisseurIce(String ice, Pageable pageable) {
        return origineRepository.findByFournisseurIceContainingIgnoreCase(ice, pageable);
    }

    public Page<Origine> searchByFournisseurNom(String fournisseurNom, Pageable pageable) {
        return origineRepository.findByFournisseurNameContainingIgnoreCase(fournisseurNom, pageable);
    }
}