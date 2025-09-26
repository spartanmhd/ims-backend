package com.example.ims_backend.service;

import com.example.ims_backend.dto.ClientDTO;
import com.example.ims_backend.entity.Client;
import com.example.ims_backend.mapper.ClientMapper;
import com.example.ims_backend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Validation based on type
    private void validateByType(Client client) {
        if (client == null) throw new IllegalArgumentException("Client ne doit pas être null");
        String type = client.getType();
        if (type == null) throw new IllegalArgumentException("Le type est requis (client ou vendeur)");
        String t = type.toLowerCase(Locale.ROOT).trim();
        if (!(t.equals("client") || t.equals("vendeur"))) {
            throw new IllegalArgumentException("Type invalide: doit être 'client' ou 'vendeur'");
        }
        if (t.equals("client")) {
            // required: nom, ice, adresse, ville, tel
            requireNotBlank(client.getNom(), "Le nom est requis pour un client");
            requireNotBlank(client.getIce(), "ICE est requis pour un client");
            requireNotBlank(client.getAdresse(), "Adresse est requise pour un client");
            requireNotBlank(client.getVille(), "Ville est requise pour un client");
            requireNotBlank(client.getTel(), "Téléphone est requis pour un client");
        } else {
            // vendeur: nom, cin, adresse, voiture, immatriculation, n permis
            requireNotBlank(client.getNom(), "Le nom est requis pour un vendeur");
            requireNotBlank(client.getCin(), "CIN est requis pour un vendeur");
            requireNotBlank(client.getAdresse(), "Adresse est requise pour un vendeur");
            requireNotBlank(client.getVoiture(), "Voiture est requise pour un vendeur");
            requireNotBlank(client.getImmatriculation(), "Immatriculation est requise pour un vendeur");
            requireNotBlank(client.getnPermis(), "N° permis est requis pour un vendeur");
        }
    }

    private void requireNotBlank(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public Page<Client> searchByNom(String nom, Pageable pageable) {
        return clientRepository.findByNomContainingIgnoreCase(nom, pageable);
    }

    public Optional<Client> getById(Integer id) {
        return clientRepository.findById(id);
    }

    public Client create(ClientDTO dto) {
        Client entity = ClientMapper.toEntity(dto);
        validateByType(entity);
        return clientRepository.save(entity);
    }

    public Optional<Client> update(Integer id, ClientDTO dto) {
        return clientRepository.findById(id).map(existing -> {
            ClientMapper.updateEntityFromDto(dto, existing);
            validateByType(existing);
            return clientRepository.save(existing);
        });
    }

    public boolean delete(Integer id) {
        return clientRepository.findById(id).map(c -> { clientRepository.delete(c); return true; }).orElse(false);
    }
}
