package com.example.ims_backend.repository;

import com.example.ims_backend.entity.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query("SELECT DISTINCT p FROM Produit p LEFT JOIN FETCH p.origines")
    List<Produit> findAllWithOrigines();
    Page<Produit> findByNomContainingIgnoreCase(String nom, Pageable pageable);
}
