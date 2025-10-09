package com.example.ims_backend.controller;

import com.example.ims_backend.dto.RetourProduitDTO;
import com.example.ims_backend.service.RetourProduitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/retours")
@CrossOrigin(origins = "http://localhost:4200")
public class RetourProduitController {

    private final RetourProduitService retourProduitService;

    public RetourProduitController(RetourProduitService retourProduitService) {
        this.retourProduitService = retourProduitService;
    }

    // Create a return providing venteId and productId
    @PostMapping
    public ResponseEntity<RetourProduitDTO> createRetour(@RequestParam Long venteId,
                                                         @RequestParam Long productId,
                                                         @RequestParam Integer quantity,
                                                         @RequestParam(required = false) LocalDate dateRetour) {
        return ResponseEntity.ok(retourProduitService.createRetour(venteId, productId, quantity, dateRetour));
    }
}
