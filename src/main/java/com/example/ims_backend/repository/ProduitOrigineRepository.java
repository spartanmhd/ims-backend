package com.example.ims_backend.repository;

import com.example.ims_backend.entity.ProduitOrigine;
import com.example.ims_backend.entity.ProduitOrigineKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitOrigineRepository extends JpaRepository<ProduitOrigine, ProduitOrigineKey> {
    List<ProduitOrigine> findByProduit_IdProduit(Long idProduit);
    void deleteAllByProduit_IdProduit(Long idProduit);
}
