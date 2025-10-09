package com.example.ims_backend.service;

import com.example.ims_backend.dto.RetourProduitDTO;
import com.example.ims_backend.entity.Produit;
import com.example.ims_backend.entity.RetourProduit;
import com.example.ims_backend.entity.VenteProduit;
import com.example.ims_backend.mapper.VenteMapper;
import com.example.ims_backend.repository.ProduitRepository;
import com.example.ims_backend.repository.RetourProduitRepository;
import com.example.ims_backend.repository.VenteProduitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class RetourProduitService {

    private final RetourProduitRepository retourProduitRepository;
    private final VenteProduitRepository venteProduitRepository;
    private final ProduitRepository produitRepository;
    private final VenteMapper venteMapper;

    public RetourProduitService(RetourProduitRepository retourProduitRepository,
                                VenteProduitRepository venteProduitRepository,
                                ProduitRepository produitRepository,
                                VenteMapper venteMapper) {
        this.retourProduitRepository = retourProduitRepository;
        this.venteProduitRepository = venteProduitRepository;
        this.produitRepository = produitRepository;
        this.venteMapper = venteMapper;
    }

    /**
     * Create a return for a specific vente and product by finding the vente_produit line and
     * ensuring we do not return more than sold minus already returned. Also updates product stock.
     */
    @Transactional
    public RetourProduitDTO createRetour(Long venteId, Long productId, Integer quantity, LocalDate dateRetour) {
        if (quantity == null || quantity <= 0) throw new IllegalArgumentException("Quantité de retour invalide");
        VenteProduit vp = venteProduitRepository.findByVente_IdAndProduit_IdProduit(venteId, productId)
                .orElseThrow(() -> new IllegalArgumentException("Ligne de vente introuvable pour ce produit"));

        Integer alreadyReturned = venteProduitRepository.totalReturnedForVenteProduit(vp.getId());
        int sold = vp.getQuantity() != null ? vp.getQuantity() : 0;
        int remaining = sold - (alreadyReturned != null ? alreadyReturned : 0);
        if (quantity > remaining) {
            throw new IllegalArgumentException("La quantité de retour dépasse la quantité vendue restante");
        }

        RetourProduit retour = new RetourProduit();
        retour.setVenteProduit(vp);
        retour.setQuantityReturned(quantity);
        retour.setDateRetour(dateRetour != null ? dateRetour : LocalDate.now());

        // Update stock back to product
        Produit produit = vp.getProduit();
        BigDecimal current = produit.getQuantite() == null ? BigDecimal.ZERO : produit.getQuantite();
        produit.setQuantite(current.add(BigDecimal.valueOf(quantity)));
        produitRepository.save(produit);

        RetourProduit saved = retourProduitRepository.save(retour);
        return venteMapper.toDTO(saved);
    }
}
