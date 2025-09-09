package com.example.ims_backend.service;

import com.example.ims_backend.entity.Fournisseur;
import com.example.ims_backend.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    public Optional<Fournisseur> getFournisseurById(Integer id) {
        return fournisseurRepository.findById(id);
    }

    public Fournisseur createFournisseur(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    public Optional<Fournisseur> updateFournisseur(Integer id, Fournisseur fournisseurDetails) {
        return fournisseurRepository.findById(id).map(fournisseur -> {
            fournisseur.setName(fournisseurDetails.getName());
            fournisseur.setIce(fournisseurDetails.getIce());
            fournisseur.setTel(fournisseurDetails.getTel());
            fournisseur.setVille(fournisseurDetails.getVille());
            fournisseur.setAdresse(fournisseurDetails.getAdresse());
            return fournisseurRepository.save(fournisseur);
        });
    }

    public boolean deleteFournisseur(Integer id) {
        return fournisseurRepository.findById(id).map(fournisseur -> {
            fournisseurRepository.delete(fournisseur);
            return true;
        }).orElse(false);
    }

    public Page<Fournisseur> searchByName(String name, Pageable pageable) {
        return fournisseurRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}