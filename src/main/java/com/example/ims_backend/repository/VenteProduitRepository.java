package com.example.ims_backend.repository;

import com.example.ims_backend.entity.VenteProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VenteProduitRepository extends JpaRepository<VenteProduit, Long> {
    List<VenteProduit> findByVente_Id(Long venteId);

    Optional<VenteProduit> findByVente_IdAndProduit_IdProduit(Long venteId, Long productId);

    @Query("select coalesce(sum(r.quantityReturned),0) from RetourProduit r where r.venteProduit.id = :venteProduitId")
    Integer totalReturnedForVenteProduit(Long venteProduitId);
}
