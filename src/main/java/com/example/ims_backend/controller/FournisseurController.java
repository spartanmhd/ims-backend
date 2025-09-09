package com.example.ims_backend.controller;

import com.example.ims_backend.entity.Fournisseur;
import com.example.ims_backend.service.FournisseurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin(origins = "http://localhost:4200")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    // Get all fournisseurs
    @GetMapping
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurService.getAllFournisseurs();
    }

    @GetMapping("/search")
    public Page<Fournisseur> searchFournisseursByName(@RequestParam String name, Pageable pageable) {
        return fournisseurService.searchByName(name, pageable);
    }

    // Get a fournisseur by id
    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable Integer id) {
        return fournisseurService.getFournisseurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new fournisseur
    @PostMapping
    public Fournisseur createFournisseur(@Valid @RequestBody Fournisseur fournisseur) {
        return fournisseurService.createFournisseur(fournisseur);
    }

    // Update a fournisseur
    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Integer id, @Valid @RequestBody Fournisseur fournisseurDetails) {
        return fournisseurService.updateFournisseur(id, fournisseurDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a fournisseur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Integer id) {
        if (fournisseurService.deleteFournisseur(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}