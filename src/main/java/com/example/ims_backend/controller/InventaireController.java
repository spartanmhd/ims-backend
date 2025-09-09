package com.example.ims_backend.controller;


import com.example.ims_backend.dto.InventaireDTO;
import com.example.ims_backend.service.InventaireService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventaire")
@CrossOrigin(origins = "http://localhost:4200")
public class InventaireController {
    private final InventaireService inventaireService;

    public InventaireController(InventaireService inventaireService) {
        this.inventaireService = inventaireService;
    }

    @GetMapping
    public List<InventaireDTO> getInventaire() {
        System.out.println("Inventaire endpoint called!");
        return inventaireService.getAllInventaire();
    }
}