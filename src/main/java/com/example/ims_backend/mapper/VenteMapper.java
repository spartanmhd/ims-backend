package com.example.ims_backend.mapper;

import com.example.ims_backend.dto.RetourProduitDTO;
import com.example.ims_backend.dto.VenteDTO;
import com.example.ims_backend.dto.VenteProduitDTO;
import com.example.ims_backend.entity.RetourProduit;
import com.example.ims_backend.entity.Vente;
import com.example.ims_backend.entity.VenteProduit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VenteMapper {

    public VenteDTO toDTO(Vente vente) {
        VenteDTO dto = new VenteDTO();
        dto.setId(vente.getId());
        if (vente.getClient() != null) {
            dto.setClientId(vente.getClient().getIdClient());
            dto.setClientNom(vente.getClient().getNom());
        }
        dto.setDatePaiement(vente.getDatePaiement());
        dto.setModePaiement(vente.getModePaiement());
        dto.setNumeroCheque(vente.getNumeroCheque());
        dto.setAvance(vente.getAvance());
        dto.setDateLivraison(vente.getDateLivraison());
        dto.setStatus(vente.getStatus() != null ? vente.getStatus().getLabel() : null);
        if (vente.getProduits() != null) {
            dto.setProduits(vente.getProduits().stream().map(this::toDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    public VenteProduitDTO toDTO(VenteProduit vp) {
        VenteProduitDTO dto = new VenteProduitDTO();
        dto.setId(vp.getId());
        if (vp.getProduit() != null) {
            dto.setProductId(vp.getProduit().getIdProduit());
            dto.setProductNom(vp.getProduit().getNom());
        }
        dto.setQuantity(vp.getQuantity());
        dto.setPrixVente(vp.getPrixVente());
        dto.setPrixAchat(vp.getPrixAchat());
        return dto;
    }

    public RetourProduitDTO toDTO(RetourProduit r) {
        RetourProduitDTO dto = new RetourProduitDTO();
        dto.setId(r.getId());
        dto.setVenteProduitId(r.getVenteProduit() != null ? r.getVenteProduit().getId() : null);
        dto.setQuantityReturned(r.getQuantityReturned());
        dto.setDateRetour(r.getDateRetour());
        return dto;
    }

    public List<VenteDTO> toDTOList(List<Vente> ventes) {
        return ventes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
