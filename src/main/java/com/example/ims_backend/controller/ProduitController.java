package com.example.ims_backend.controller;

import com.example.ims_backend.dto.ProduitRequestDTO;
import com.example.ims_backend.dto.ProduitResponseDTO;
import com.example.ims_backend.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "http://localhost:4200")
public class ProduitController {
    private final ProduitService produitService;

    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @PostMapping
    public ResponseEntity<ProduitResponseDTO> createProduit(@RequestBody ProduitRequestDTO dto) {
        ProduitResponseDTO savedProduit = produitService.createProduit(dto);
        return ResponseEntity.ok(savedProduit);
    }

    @GetMapping
    public List<ProduitResponseDTO> getAllProduits() {
        return produitService.getAllProduits();
    }

    @GetMapping("/search")
    public Page<ProduitResponseDTO> searchProduits(@RequestParam String nom, Pageable pageable) {
        Page<com.example.ims_backend.entity.Produit> produits = produitService.searchProduitsByName(nom, pageable);
        return produits.map(produit -> produitService.getProduit(produit.getIdProduit()));
    }

    @GetMapping("/{id}")
    public ProduitResponseDTO getProduit(@PathVariable Long id) {
        return produitService.getProduit(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitResponseDTO> updateProduit(@PathVariable Long id, @RequestBody ProduitRequestDTO dto) {
        ProduitResponseDTO updatedProduit = produitService.updateProduit(id, dto);
        return ResponseEntity.ok(updatedProduit);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<ProduitResponseDTO> patchProduit(@PathVariable Long id, @RequestBody ProduitRequestDTO dto) {
        ProduitResponseDTO updated = produitService.patchProduit(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to list only products with quantity > 0 for vente creation
    @GetMapping("/available")
    public List<ProduitResponseDTO> getAvailableProduits() {
        return produitService.getAvailableProduits();
    }
}