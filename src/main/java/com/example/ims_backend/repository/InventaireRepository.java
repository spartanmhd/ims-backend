package com.example.ims_backend.repository;

import com.example.ims_backend.entity.Inventaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventaireRepository extends JpaRepository<Inventaire, Integer> {
}
