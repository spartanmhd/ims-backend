package com.example.ims_backend.controller;

import com.example.ims_backend.entity.Origine;
import com.example.ims_backend.service.OrigineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/origines")
@CrossOrigin(origins = "http://localhost:4200")
public class OrigineController {

    @Autowired
    private OrigineService service;

    // Create a new origine (no quantite, no prixAchat)
    @PostMapping
    public Origine create(@Valid @RequestBody Origine origine) {
        return service.saveOrigine(origine);
    }

    // Update an existing origine (no quantite, no prixAchat)
    @PutMapping("/{id}")
    public ResponseEntity<Origine> update(@PathVariable Integer id, @RequestBody Origine origine) {
        return service.updateOrigine(id, origine)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all origines
    @GetMapping
    public Page<Origine> list(Pageable pageable) {
        return service.getAllOrigines(pageable);
    }

    // Search origines by nom or fournisseur ICE
    @GetMapping("/search")
    public Page<Origine> search(@RequestParam(required = false) String nom,
                                @RequestParam(required = false) String ice, Pageable pageable) {
        if (nom != null) return service.searchByNom(nom, pageable);
        if (ice != null) return service.searchByFournisseurIce(ice, pageable);
        return service.getAllOrigines(pageable);
    }

    @GetMapping("/searchByFournisseurNom")
    public Page<Origine> searchByFournisseurNom(@RequestParam String fournisseurNom, Pageable pageable) {
        return service.searchByFournisseurNom(fournisseurNom, pageable);
    }

    // Get a single origine by id
    @GetMapping("/{id}")
    public ResponseEntity<Origine> get(@PathVariable Integer id) {
        return service.getOrigine(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an origine
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.deleteOrigine(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}