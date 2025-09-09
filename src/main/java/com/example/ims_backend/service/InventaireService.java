package com.example.ims_backend.service;


import com.example.ims_backend.dto.InventaireDTO;
import com.example.ims_backend.entity.Inventaire;
import com.example.ims_backend.repository.InventaireRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventaireService {
    private final InventaireRepository inventaireRepository;

    public InventaireService(InventaireRepository inventaireRepository) {
        this.inventaireRepository = inventaireRepository;
    }

    public List<InventaireDTO> getAllInventaire() {
        return inventaireRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private InventaireDTO toDTO(Inventaire entity) {
        InventaireDTO dto = new InventaireDTO();
        dto.setId_inventaire(entity.getId_inventaire());
        dto.setType_stock(entity.getType_stock());
        dto.setId_reference(entity.getId_reference());
        dto.setQuantite_reelle(entity.getQuantite_reelle());
        dto.setQuantite_systeme(entity.getQuantite_systeme());
        dto.setReference_name(entity.getReference_name ());
        return dto;
    }
}