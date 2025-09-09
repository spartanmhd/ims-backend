package com.example.ims_backend.repository;

import com.example.ims_backend.entity.Achat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AchatRepository extends JpaRepository<Achat, Integer> {

    @Query("SELECT a FROM Achat a JOIN FETCH a.fournisseur f JOIN FETCH a.origine o")
    Page<Achat> findAllWithFournisseurAndOrigine(Pageable pageable);

    @Query("SELECT a FROM Achat a JOIN a.fournisseur f JOIN a.origine o " +
            "WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :fournisseurName, '%')) " +
            "AND LOWER(o.nom) LIKE LOWER(CONCAT('%', :origineName, '%')) " +
            "ORDER BY a.dateAchat DESC")
    Page<Achat> findByFournisseurNameAndOrigineNameOrderByDateAchatDesc(
            @Param("fournisseurName") String fournisseurName,
            @Param("origineName") String origineName,
            Pageable pageable
    );

    // Search by dateAchat
    Page<Achat> findByDateAchat(java.time.LocalDate dateAchat, Pageable pageable);

    // Search by numeroBL (partial match)
    Page<Achat> findByNumeroBLContainingIgnoreCase(String numeroBL, Pageable pageable);

    // List achats by newest date
    Page<Achat> findAllByOrderByDateAchatDesc(Pageable pageable);

    // List achats by latest added (PK descending)
}