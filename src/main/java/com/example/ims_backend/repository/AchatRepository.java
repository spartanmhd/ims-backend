package com.example.ims_backend.repository;

import com.example.ims_backend.entity.Achat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AchatRepository extends JpaRepository<Achat, Integer> {

    // Do NOT override findAll(Pageable) with a @Query using JOIN FETCH!
    // The default findAll(Pageable) is fine.

    // Find all Achats for a fournisseur name and ligne origine name (case-insensitive)
    @Query("SELECT DISTINCT a FROM Achat a JOIN a.fournisseur f JOIN a.achatOrigines ao JOIN ao.origine o " +
            "WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :fournisseurName, '%')) " +
            "AND LOWER(o.nom) LIKE LOWER(CONCAT('%', :origineName, '%')) " +
            "ORDER BY a.dateAchat DESC")
    Page<Achat> findAchatsByFournisseurAndOrigineName(
            @Param("fournisseurName") String fournisseurName,
            @Param("origineName") String origineName,
            Pageable pageable
    );

    Page<Achat> findByDateAchat(java.time.LocalDate dateAchat, Pageable pageable);

    Page<Achat> findByNumeroBLContainingIgnoreCase(String numeroBL, Pageable pageable);

    Page<Achat> findAllByOrderByDateAchatDesc(Pageable pageable);

    @Query("SELECT a FROM Achat a JOIN a.achatOrigines ao WHERE ao.origine.idOrigine = :idOrigine ORDER BY a.dateAchat DESC, a.idAchat DESC")
    Page<Achat> findTopByOrigineIdOrderByDateAchatDescIdAchatDesc(@Param("idOrigine") Integer idOrigine, Pageable pageable);
}