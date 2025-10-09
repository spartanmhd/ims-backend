package com.example.ims_backend.controller;

import com.example.ims_backend.dto.VenteCreateRequestDTO;
import com.example.ims_backend.dto.VenteDTO;
import com.example.ims_backend.service.VenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventes")
@CrossOrigin(origins = "http://localhost:4200")
public class VenteController {

    private final VenteService venteService;

    public VenteController(VenteService venteService) {
        this.venteService = venteService;
    }

    // Create a vente with multiple products
    @PostMapping
    public ResponseEntity<VenteDTO> createVente(@RequestBody VenteCreateRequestDTO request) {
        return ResponseEntity.ok(venteService.createVente(request));
    }

    @GetMapping
    public List<VenteDTO> listVentes() { return venteService.listVentes(); }

    @GetMapping("/{id}")
    public VenteDTO getVente(@PathVariable Long id) { return venteService.getVente(id); }

    // Update vente (minimal fields, not modifying line items)
    @PutMapping("/{id}")
    public ResponseEntity<VenteDTO> updateVente(@PathVariable Long id, @RequestBody VenteCreateRequestDTO request) {
        return ResponseEntity.ok(venteService.updateVente(id, request));
    }

    // Delete vente (restores stock)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVente(@PathVariable Long id) {
        venteService.deleteVente(id);
        return ResponseEntity.noContent().build();
    }

    // Search ventes by client name
    @GetMapping("/search")
    public List<VenteDTO> searchByClientName(@RequestParam("clientName") String clientName) {
        return venteService.listVentesByClientName(clientName);
    }
}
