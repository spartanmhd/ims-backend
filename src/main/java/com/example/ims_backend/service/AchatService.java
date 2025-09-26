package com.example.ims_backend.service;

import com.example.ims_backend.dto.AchatDTO;
import com.example.ims_backend.entity.Achat;
import com.example.ims_backend.entity.AchatOrigine;
import com.example.ims_backend.entity.Origine;
import com.example.ims_backend.dto.AchatOrigineDTO;
import com.example.ims_backend.mapper.AchatMapper;
import com.example.ims_backend.repository.AchatRepository;
import com.example.ims_backend.repository.AchatOrigineRepository;
import com.example.ims_backend.repository.OrigineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors; // <-- ADD THIS

@Service
public class AchatService {

    @Autowired
    private AchatRepository achatRepository;

    @Autowired
    private AchatOrigineRepository achatOrigineRepository;

    @Autowired
    private OrigineRepository origineRepository;
    @Autowired
    private AchatMapper achatMapper;

    /** Create a new Achat with multiple Origine lines */
    @Transactional
    public Achat saveAchatWithOrigines(Achat achat, List<AchatOrigineDTO> achatOrigineDtos) {
        Achat savedAchat = achatRepository.save(achat);

        List<AchatOrigine> achatOrigines = new ArrayList<>();
        for (AchatOrigineDTO dto : achatOrigineDtos) {

            Origine origine = origineRepository.findById(dto.getOrigineId())
                    .orElseThrow(() -> new IllegalArgumentException("Origine not found: " + dto.getOrigineId()));

            System.out.println("Fetched origine id: " + origine.getIdOrigine());

            // Initialize stock if null
            if (origine.getStock() == null) {
                origine.setStock(0);
            }
            // Update the stock using BigDecimal
            origine.setStock(origine.getStock() + dto.getQuantite().intValue());
            origineRepository.save(origine);

            AchatOrigine achatOrigine = new AchatOrigine();
            achatOrigine.setAchat(savedAchat);
            achatOrigine.setOrigine(origine);
            achatOrigine.setPrixAchat(dto.getPrixAchat());
            achatOrigine.setQuantite(dto.getQuantite());
            achatOrigines.add(achatOrigine);
        }
        achatOrigineRepository.saveAll(achatOrigines);

        return savedAchat;
    }
    /** Update an existing Achat and its lignes */
    @Transactional
    public Optional<Achat> updateAchatWithOrigines(Integer id, Achat achat, List<AchatOrigineDTO> achatOrigineDtos) {
        return achatRepository.findById(id).map(existing -> {
            achat.setIdAchat(existing.getIdAchat());
            // Copy/merge any additional needed fields here
            Achat updatedAchat = achatRepository.save(achat);
            // Remove old lignes and save new ones
            achatOrigineRepository.deleteAllByAchat_IdAchat(updatedAchat.getIdAchat());
            List<AchatOrigine> achatOrigines = new ArrayList<>();
            for (AchatOrigineDTO dto : achatOrigineDtos) {
                Origine origine = origineRepository.findById(dto.getOrigineId())
                        .orElseThrow(() -> new IllegalArgumentException("Origine not found: " + dto.getOrigineId()));
                AchatOrigine achatOrigine = new AchatOrigine();
                achatOrigine.setAchat(updatedAchat);
                achatOrigine.setOrigine(origine);
                achatOrigine.setPrixAchat(dto.getPrixAchat());
                achatOrigine.setQuantite(dto.getQuantite());
                achatOrigines.add(achatOrigine);
            }
            achatOrigineRepository.saveAll(achatOrigines);
            return updatedAchat;
        });
    }

    @Transactional(readOnly = true)
    public List<AchatDTO> getAllAchatWithOrigines(){
        List<Achat> achats = achatRepository.findAllWithAchatOrigines();
        return achats.stream()
                .map(a -> achatMapper.toDTO(a, achatOrigineRepository.findByAchat_IdAchat(a.getIdAchat())))
                .collect(Collectors.toList());
    }

    /** Get all Achats (paginated) */
    public Page<Achat> findAll(Pageable pageable) {
        return achatRepository.findAll(pageable);
    }

    public Optional<Achat> findById(Integer id) {
        return achatRepository.findById(id);
    }

    public List<Origine> getOriginesForFournisseur(Integer idFournisseur) {
        return origineRepository.findByFournisseur_IdFournisseur(idFournisseur);
    }

    @Transactional
    public boolean deleteAchat(Integer id) {
        Optional<Achat> achat = achatRepository.findById(id);
        if (achat.isPresent()) {
            achatOrigineRepository.deleteAllByAchat_IdAchat(id);
            achatRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /** All lignes for an achat */
    public List<AchatOrigine> getAchatOriginesForAchat(Integer achatId) {
        return achatOrigineRepository.findByAchat_IdAchat(achatId);
    }

    /** Example: find by fournisseur and origine name (requires custom query in AchatRepository) */
    public Page<Achat> findAchatsByFournisseurAndOrigineName(String fournisseurName, String origineName, Pageable pageable) {
        return achatRepository.findAchatsByFournisseurAndOrigineName(fournisseurName, origineName, pageable);
    }

    public Page<Achat> searchByDateAchat(LocalDate date, Pageable pageable) {
        return achatRepository.findByDateAchat(date, pageable);
    }

    public Page<Achat> searchByNumeroBL(String numeroBL, Pageable pageable) {
        return achatRepository.findByNumeroBLContainingIgnoreCase(numeroBL, pageable);
    }

    public Page<Achat> getAchatsByDateDesc(Pageable pageable) {
        return achatRepository.findAllByOrderByDateAchatDesc(pageable);
    }

}