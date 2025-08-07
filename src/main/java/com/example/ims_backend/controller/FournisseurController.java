package com.example.ims_backend.controller;

import com.example.ims_backend.entity.Fournisseur;
import com.example.ims_backend.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    // Get all fournisseurs
    @GetMapping
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    // Get a fournisseur by id
    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable Integer id) {
        return fournisseurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new fournisseur
    @PostMapping
    public Fournisseur createFournisseur(@RequestBody Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    // Update a fournisseur
    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Integer id, @RequestBody Fournisseur fournisseurDetails) {
        return fournisseurRepository.findById(id)
                .map(fournisseur -> {
                    fournisseur.setIce(fournisseurDetails.getIce());
                    fournisseur.setTel(fournisseurDetails.getTel());
                    fournisseur.setVille(fournisseurDetails.getVille());
                    fournisseur.setAdresse(fournisseurDetails.getAdresse());
                    Fournisseur updatedFournisseur = fournisseurRepository.save(fournisseur);
                    return ResponseEntity.ok(updatedFournisseur);
                }).orElse(ResponseEntity.notFound().build());
    }

    // Delete a fournisseur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Integer id) {
        return fournisseurRepository.findById(id)
                .map(fournisseur -> {
                    fournisseurRepository.delete(fournisseur);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
