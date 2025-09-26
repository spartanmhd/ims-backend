package com.example.ims_backend.repository;

import com.example.ims_backend.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Page<Client> findByNomContainingIgnoreCase(String nom, Pageable pageable);
}
