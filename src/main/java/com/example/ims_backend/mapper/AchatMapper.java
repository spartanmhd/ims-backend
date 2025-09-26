package com.example.ims_backend.mapper;

import com.example.ims_backend.dto.AchatDTO;
import com.example.ims_backend.dto.AchatOrigineDTO;
import com.example.ims_backend.dto.AchatRequestDTO;
import com.example.ims_backend.dto.OrigineDTO;
import com.example.ims_backend.entity.Achat;
import com.example.ims_backend.entity.AchatOrigine;
import com.example.ims_backend.entity.Fournisseur;
import com.example.ims_backend.entity.Origine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AchatMapper {

    public Achat toEntity(AchatRequestDTO dto) {
        Achat achat = new Achat();
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setIdFournisseur(dto.getFournisseurId());
        achat.setFournisseur(fournisseur);
        achat.setNumeroBL(dto.getNumeroBL());
        achat.setModePaiement(dto.getModePaiement());
        achat.setNumeroCheque(dto.getNumeroCheque());
        achat.setDateAchat(dto.getDateAchat());
        achat.setDatePaiement(dto.getDatePaiement());
        achat.setAvance(dto.getAvance());
        return achat;
    }

    public AchatDTO toDTO(Achat achat, List<AchatOrigine> origines) {
        AchatDTO dto = new AchatDTO();
        dto.setIdAchat(achat.getIdAchat());
        dto.setFournisseurId(achat.getFournisseur() != null ? achat.getFournisseur().getIdFournisseur() : null);
        dto.setFournisseurName(achat.getFournisseur() != null ? achat.getFournisseur().getName() : null);
        dto.setNumeroBL(achat.getNumeroBL());
        dto.setModePaiement(achat.getModePaiement());
        dto.setNumeroCheque(achat.getNumeroCheque());
        dto.setDateAchat(achat.getDateAchat());
        dto.setDatePaiement(achat.getDatePaiement());
        dto.setAvance(achat.getAvance());
        dto.setOrigines(origines.stream().map(this::origineToDTO).collect(Collectors.toList()));
        dto.setTotalPrixTTC(achat.getTotalPrixTTC());
        return dto;
    }

    public AchatOrigineDTO origineToDTO(AchatOrigine origine) {
        AchatOrigineDTO dto = new AchatOrigineDTO();
        dto.setOrigineId(origine.getOrigine().getIdOrigine());
        dto.setPrixAchat(origine.getPrixAchat());
        dto.setQuantite(origine.getQuantite());
        // Set stock from Origine entity (if needed in DTO)
        if (origine.getOrigine() != null) {
            dto.setStock(origine.getOrigine().getStock());
        }
        return dto;
    }

    // Optional: for mapping Origine directly to OrigineDTO (for lists, etc.)
    public OrigineDTO origineToDTO(Origine origine) {
        if (origine == null) return null;
        OrigineDTO dto = new OrigineDTO();
        dto.setIdOrigine(origine.getIdOrigine());
        dto.setNom(origine.getNom());
        dto.setStock(origine.getStock());
        if (origine.getFournisseur() != null) {
            dto.setIdFournisseur(origine.getFournisseur().getIdFournisseur());
            dto.setFournisseurName(origine.getFournisseur().getName());
        }
        return dto;
    }
}