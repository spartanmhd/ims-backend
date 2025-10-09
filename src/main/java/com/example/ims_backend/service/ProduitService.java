package com.example.ims_backend.service;

import com.example.ims_backend.dto.ProduitOrigineDTO;
import com.example.ims_backend.dto.ProduitRequestDTO;
import com.example.ims_backend.dto.ProduitResponseDTO;
import com.example.ims_backend.entity.Origine;
import com.example.ims_backend.entity.Produit;
import com.example.ims_backend.entity.ProduitOrigine;
import com.example.ims_backend.entity.ProduitOrigineKey;
import com.example.ims_backend.repository.OrigineRepository;
import com.example.ims_backend.repository.ProduitRepository;
import com.example.ims_backend.repository.ProduitOrigineRepository;
import com.example.ims_backend.repository.AchatOrigineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final OrigineRepository origineRepository;
    private final ProduitOrigineRepository produitOrigineRepository;
    private final AchatOrigineRepository achatOrigineRepository;

    @Autowired
    public ProduitService(
            ProduitRepository produitRepository,
            OrigineRepository origineRepository,
            ProduitOrigineRepository produitOrigineRepository,
            AchatOrigineRepository achatOrigineRepository
    ) {
        this.produitRepository = produitRepository;
        this.origineRepository = origineRepository;
        this.produitOrigineRepository = produitOrigineRepository;
        this.achatOrigineRepository = achatOrigineRepository;
    }

    private BigDecimal latestPrixAchatForOrigine(Integer origineId) {
        var achatOrigine = achatOrigineRepository.findTopByOrigine_IdOrigineOrderByAchat_DateAchatDescAchat_IdAchatDesc(origineId);
        return achatOrigine != null && achatOrigine.getPrixAchat() != null ? achatOrigine.getPrixAchat() : BigDecimal.ZERO;
    }

    @Transactional
    public ProduitResponseDTO createProduit(ProduitRequestDTO dto) {
        Produit produit = new Produit();
        produit.setNom(dto.getNom());
        produit.setMarque(dto.getMarque());
        produit.setEmballage(dto.getEmballage());
        produit.setAutres(dto.getAutres());
        produit.setQuantite(dto.getQuantite());

        List<ProduitOrigine> origines = new ArrayList<>();
        BigDecimal totalProportion = BigDecimal.ZERO;
        BigDecimal prixAchatMelange = BigDecimal.ZERO;

        if (dto.getOrigines() == null || dto.getOrigines().isEmpty()) {
            throw new IllegalArgumentException("La liste des origines ne doit pas être vide.");
        }

        for (ProduitOrigineDTO poDto : dto.getOrigines()) {
            Origine origine = origineRepository.findByNom(poDto.getOrigineNom())
                    .orElseThrow(() -> new IllegalArgumentException("Origine introuvable: " + poDto.getOrigineNom()));
            ProduitOrigine po = new ProduitOrigine();
            po.setProduit(produit);
            po.setOrigine(origine);
            po.setProportion(poDto.getProportion());
            po.setId(new ProduitOrigineKey(null, origine.getIdOrigine()));
            origines.add(po);

            totalProportion = totalProportion.add(poDto.getProportion());
            prixAchatMelange = prixAchatMelange.add(latestPrixAchatForOrigine(origine.getIdOrigine()).multiply(poDto.getProportion()));
        }

        // Check composition for 1kg recipe
        BigDecimal allowedError = new BigDecimal("0.01");
        BigDecimal diff = totalProportion.subtract(BigDecimal.ONE).abs();
        if (diff.compareTo(allowedError) > 0) {
            throw new IllegalArgumentException("La somme des proportions du mélange doit être exactement 1 (pour une recette de 1kg). Valeur reçue: " + totalProportion);
        }

        // prixAchatMelange is for 1kg
        BigDecimal prixAchat1kg = prixAchatMelange;
        if (produit.getEmballage() != null) {
            prixAchat1kg = prixAchat1kg.add(produit.getEmballage());
        }
        if (produit.getAutres() != null) {
            prixAchat1kg = prixAchat1kg.add(produit.getAutres());
        }
        produit.setPrixAchat(prixAchat1kg);

        // Prix de vente is not set at recipe creation
        produit.setPrixVente(null);

        Produit savedProduit = produitRepository.save(produit);

        for (ProduitOrigine po : origines) {
            po.setProduit(savedProduit);
            po.setId(new ProduitOrigineKey(savedProduit.getIdProduit(), po.getOrigine().getIdOrigine()));
            produitOrigineRepository.save(po);
        }

        return toResponseDTO(savedProduit, origines);
    }

    public ProduitResponseDTO toResponseDTO(Produit produit, List<ProduitOrigine> origines) {
        ProduitResponseDTO dto = new ProduitResponseDTO();
        dto.setIdProduit(produit.getIdProduit());
        dto.setNom(produit.getNom());
        dto.setMarque(produit.getMarque());
        dto.setEmballage(produit.getEmballage());
        dto.setAutres(produit.getAutres());
        dto.setQuantite(produit.getQuantite());
        dto.setPrixAchat(produit.getPrixAchat());
        dto.setPrixVente(produit.getPrixVente());

        List<ProduitOrigineDTO> origineDTOs = new ArrayList<>();
        for (ProduitOrigine po : origines) {
            ProduitOrigineDTO poDto = new ProduitOrigineDTO();
            poDto.setIdOrigine(po.getOrigine().getIdOrigine());
            poDto.setOrigineNom(po.getOrigine().getNom());
            poDto.setProportion(po.getProportion());
            origineDTOs.add(poDto);
        }
        dto.setOrigines(origineDTOs);
        return dto;
    }

    public List<ProduitResponseDTO> getAllProduits() {
        List<Produit> produits = produitRepository.findAllWithOrigines();
        List<ProduitResponseDTO> dtos = new ArrayList<>();
        for (Produit produit : produits) {
            List<ProduitOrigine> origines = produit.getOrigines();
            dtos.add(toResponseDTO(produit, origines));
        }
        return dtos;
    }

    public ProduitResponseDTO getProduit(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));
        List<ProduitOrigine> origines = produitOrigineRepository.findByProduit_IdProduit(id);
        return toResponseDTO(produit, origines);
    }

    @Transactional
    public ProduitResponseDTO updateProduit(Long id, ProduitRequestDTO dto) {
        Produit existingProduit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));

        // Update metadata fields
        existingProduit.setNom(dto.getNom());
        existingProduit.setMarque(dto.getMarque());
        existingProduit.setEmballage(dto.getEmballage());
        existingProduit.setAutres(dto.getAutres());
        existingProduit.setQuantite(dto.getQuantite());

        List<ProduitOrigine> newOrigines = new ArrayList<>();
        BigDecimal totalProportion = BigDecimal.ZERO;
        BigDecimal prixAchatMelange = BigDecimal.ZERO;

        for (ProduitOrigineDTO poDto : dto.getOrigines()) {
            Origine origine = origineRepository.findByNom(poDto.getOrigineNom())
                    .orElseThrow(() -> new IllegalArgumentException("Origine introuvable: " + poDto.getOrigineNom()));

            ProduitOrigine po = new ProduitOrigine();
            po.setProduit(existingProduit);
            po.setOrigine(origine);
            po.setProportion(poDto.getProportion());
            po.setId(new ProduitOrigineKey(existingProduit.getIdProduit(), origine.getIdOrigine()));
            newOrigines.add(po);

            totalProportion = totalProportion.add(poDto.getProportion());
            prixAchatMelange = prixAchatMelange.add(latestPrixAchatForOrigine(origine.getIdOrigine()).multiply(poDto.getProportion()));

        }

        // Check proportions
        BigDecimal allowedError = new BigDecimal("0.01");
        BigDecimal diff = totalProportion.subtract(BigDecimal.ONE).abs();
        if (diff.compareTo(allowedError) > 0) {
            throw new IllegalArgumentException("La somme des proportions du mélange doit être exactement 1. Valeur reçue: " + totalProportion);
        }

        // Set prixAchat
        BigDecimal prixAchat1kg = prixAchatMelange;
        if (existingProduit.getEmballage() != null) {
            prixAchat1kg = prixAchat1kg.add(existingProduit.getEmballage());
        }
        if (existingProduit.getAutres() != null) {
            prixAchat1kg = prixAchat1kg.add(existingProduit.getAutres());
        }
        existingProduit.setPrixAchat(prixAchat1kg);

        // Remove old origins and add new ones
        produitOrigineRepository.deleteAllByProduit_IdProduit(id);
        for (ProduitOrigine po : newOrigines) {
            produitOrigineRepository.save(po);
        }

        Produit savedProduit = produitRepository.save(existingProduit);
        return toResponseDTO(savedProduit, newOrigines);
    }

    @Transactional
    public ProduitResponseDTO patchProduit(Long id, ProduitRequestDTO dto) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));

        BigDecimal oldQuantite = produit.getQuantite();
        BigDecimal newQuantite = dto.getQuantite();

        if (newQuantite != null && (oldQuantite == null || !newQuantite.equals(oldQuantite))) {
            produit.setQuantite(newQuantite);
        }

        // Patch other fields
        if (dto.getNom() != null) produit.setNom(dto.getNom());
        if (dto.getMarque() != null) produit.setMarque(dto.getMarque());
        if (dto.getEmballage() != null) produit.setEmballage(dto.getEmballage());
        if (dto.getAutres() != null) produit.setAutres(dto.getAutres());
        if (dto.getPrixVente() != null) produit.setPrixVente(dto.getPrixVente());

        Produit savedProduit = produitRepository.save(produit);
        List<ProduitOrigine> origines = produitOrigineRepository.findByProduit_IdProduit(id);
        return toResponseDTO(savedProduit, origines);
    }

    @Transactional
    public void deleteProduit(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));

        produitOrigineRepository.deleteAllByProduit_IdProduit(id);
        produitRepository.deleteById(id);
    }

    public Page<Produit> searchProduitsByName(String nom, Pageable pageable) {
        return produitRepository.findByNomContainingIgnoreCase(nom, pageable);
    }

    public List<ProduitResponseDTO> getAvailableProduits() {
        List<Produit> produits = produitRepository.findByQuantiteGreaterThan(BigDecimal.ZERO);
        List<ProduitResponseDTO> dtos = new ArrayList<>();
        for (Produit produit : produits) {
            List<ProduitOrigine> origines = produitOrigineRepository.findByProduit_IdProduit(produit.getIdProduit());
            dtos.add(toResponseDTO(produit, origines));
        }
        return dtos;
    }
}