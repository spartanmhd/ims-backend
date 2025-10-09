package com.example.ims_backend.service;

import com.example.ims_backend.dto.VenteCreateRequestDTO;
import com.example.ims_backend.dto.VenteDTO;
import com.example.ims_backend.entity.Client;
import com.example.ims_backend.entity.Produit;
import com.example.ims_backend.entity.Vente;
import com.example.ims_backend.entity.VenteProduit;
import com.example.ims_backend.mapper.VenteMapper;
import com.example.ims_backend.repository.ClientRepository;
import com.example.ims_backend.repository.ProduitRepository;
import com.example.ims_backend.repository.VenteProduitRepository;
import com.example.ims_backend.repository.VenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Service
public class VenteService {

    private final VenteRepository venteRepository;
    private final VenteProduitRepository venteProduitRepository;
    private final ProduitRepository produitRepository;
    private final ClientRepository clientRepository;
    private final VenteMapper venteMapper;

    public VenteService(VenteRepository venteRepository,
                        VenteProduitRepository venteProduitRepository,
                        ProduitRepository produitRepository,
                        ClientRepository clientRepository,
                        VenteMapper venteMapper) {
        this.venteRepository = venteRepository;
        this.venteProduitRepository = venteProduitRepository;
        this.produitRepository = produitRepository;
        this.clientRepository = clientRepository;
        this.venteMapper = venteMapper;
    }

    private boolean isCheque(String modePaiement) {
        if (modePaiement == null) return false;
        String normalized = Normalizer.normalize(modePaiement, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return "cheque".equalsIgnoreCase(normalized.trim());
    }

    @Transactional
    public VenteDTO createVente(VenteCreateRequestDTO request) {
        if (request.getClientId() == null)
            throw new IllegalArgumentException("clientId est requis");
        if (request.getItems() == null || request.getItems().isEmpty())
            throw new IllegalArgumentException("Au moins un produit est requis");
        if ("espèce".equalsIgnoreCase(request.getModePaiement()) && (request.getAvance() == null)) {
            throw new IllegalArgumentException("L'avance est requise lorsque le mode de paiement est 'espèce'");
        }
        if (isCheque(request.getModePaiement()) && (request.getNumeroCheque() == null || request.getNumeroCheque().trim().isEmpty())) {
            throw new IllegalArgumentException("Le numéro de chèque est requis lorsque le mode de paiement est 'cheque'");
        }

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable"));

        Vente vente = new Vente();
        vente.setClient(client);
        vente.setDatePaiement(request.getDatePaiement());
        vente.setModePaiement(request.getModePaiement());

        // Only set avance if modePaiement is "espèce"
        if ("espèce".equalsIgnoreCase(request.getModePaiement())) {
            vente.setAvance(request.getAvance());
        } else {
            vente.setAvance(null);
        }

        // Only set numeroCheque if modePaiement is "cheque"
        if (isCheque(request.getModePaiement())) {
            vente.setNumeroCheque(request.getNumeroCheque());
        } else {
            vente.setNumeroCheque(null);
        }

        vente.setDateLivraison(request.getDateLivraison());
        if (request.getStatus() != null) {
            if ("payé".equalsIgnoreCase(request.getStatus())) {
                vente.setStatus(Vente.Status.PAYE);
            } else {
                vente.setStatus(Vente.Status.NON_PAYE);
            }
        } else {
            vente.setStatus(Vente.Status.NON_PAYE);
        }

        List<VenteProduit> lignes = new ArrayList<>();
        for (VenteCreateRequestDTO.Item item : request.getItems()) {
            Produit produit = produitRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Produit introuvable: id=" + item.getProductId()));
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantité invalide pour le produit id=" + item.getProductId());
            }
            BigDecimal stock = produit.getQuantite() == null ? BigDecimal.ZERO : produit.getQuantite();
            if (stock.compareTo(BigDecimal.valueOf(item.getQuantity())) < 0) {
                throw new IllegalArgumentException("Stock insuffisant pour le produit id=" + item.getProductId());
            }

            VenteProduit vp = new VenteProduit();
            vp.setVente(vente);
            vp.setProduit(produit);
            vp.setQuantity(item.getQuantity());
            vp.setPrixVente(item.getPrixVente());
            vp.setPrixAchat(produit.getPrixAchat());
            lignes.add(vp);

            produit.setQuantite(stock.subtract(BigDecimal.valueOf(item.getQuantity())));
            produitRepository.save(produit);
        }
        vente.setProduits(lignes);

        Vente saved = venteRepository.save(vente);
        return venteMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public VenteDTO getVente(Long id) {
        Vente v = venteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vente introuvable"));
        return venteMapper.toDTO(v);
    }

    @Transactional(readOnly = true)
    public List<VenteDTO> listVentes() {
        return venteMapper.toDTOList(venteRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<VenteDTO> listVentesByClientName(String clientName) {
        return venteMapper.toDTOList(venteRepository.findByClient_NomContainingIgnoreCase(clientName));
    }

    @Transactional
    public VenteDTO updateVente(Long id, VenteCreateRequestDTO request) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vente introuvable"));

        if (request.getClientId() != null) {
            Client client = clientRepository.findById(request.getClientId())
                    .orElseThrow(() -> new IllegalArgumentException("Client introuvable"));
            vente.setClient(client);
        }
        if (request.getDatePaiement() != null) vente.setDatePaiement(request.getDatePaiement());
        if (request.getModePaiement() != null) vente.setModePaiement(request.getModePaiement());

        // Update avance only if modePaiement is "espèce"
        if ("espèce".equalsIgnoreCase(request.getModePaiement())) {
            vente.setAvance(request.getAvance());
        } else {
            vente.setAvance(null);
        }

        // Update numeroCheque only if modePaiement is "cheque"
        String effectiveMode = request.getModePaiement() != null ? request.getModePaiement() : vente.getModePaiement();
        if (isCheque(effectiveMode)) {
            if (request.getNumeroCheque() != null) {
                vente.setNumeroCheque(request.getNumeroCheque());
            }
        } else {
            vente.setNumeroCheque(null);
        }

        if (request.getDateLivraison() != null) vente.setDateLivraison(request.getDateLivraison());
        if (request.getStatus() != null) {
            if ("payé".equalsIgnoreCase(request.getStatus())) {
                vente.setStatus(Vente.Status.PAYE);
            } else {
                vente.setStatus(Vente.Status.NON_PAYE);
            }
        }

        Vente saved = venteRepository.save(vente);
        return venteMapper.toDTO(saved);
    }

    @Transactional
    public void deleteVente(Long id) {
        Vente vente = venteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vente introuvable"));
        if (vente.getProduits() != null) {
            for (VenteProduit vp : vente.getProduits()) {
                Produit p = vp.getProduit();
                BigDecimal stock = p.getQuantite() == null ? BigDecimal.ZERO : p.getQuantite();
                BigDecimal toRestore = vp.getQuantity() == null ? BigDecimal.ZERO : BigDecimal.valueOf(vp.getQuantity());
                p.setQuantite(stock.add(toRestore));
                produitRepository.save(p);
            }
        }
        venteRepository.delete(vente);
    }
}