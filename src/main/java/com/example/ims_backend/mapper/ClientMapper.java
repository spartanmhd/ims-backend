package com.example.ims_backend.mapper;

import com.example.ims_backend.dto.ClientDTO;
import com.example.ims_backend.entity.Client;

public class ClientMapper {

    public static ClientDTO toDto(Client entity) {
        if (entity == null) return null;
        ClientDTO dto = new ClientDTO();
        dto.setIdClient(entity.getIdClient());
        dto.setType(entity.getType());
        dto.setNom(entity.getNom());
        dto.setCin(entity.getCin());
        dto.setIce(entity.getIce());
        dto.setAdresse(entity.getAdresse());
        dto.setVille(entity.getVille());
        dto.setVoiture(entity.getVoiture());
        dto.setImmatriculation(entity.getImmatriculation());
        dto.setnPermis(entity.getnPermis());
        dto.setTel(entity.getTel());
        return dto;
    }

    public static Client toEntity(ClientDTO dto) {
        if (dto == null) return null;
        Client entity = new Client();
        entity.setIdClient(dto.getIdClient());
        entity.setType(dto.getType());
        entity.setNom(dto.getNom());
        entity.setCin(dto.getCin());
        entity.setIce(dto.getIce());
        entity.setAdresse(dto.getAdresse());
        entity.setVille(dto.getVille());
        entity.setVoiture(dto.getVoiture());
        entity.setImmatriculation(dto.getImmatriculation());
        entity.setnPermis(dto.getnPermis());
        entity.setTel(dto.getTel());
        return entity;
    }

    public static void updateEntityFromDto(ClientDTO dto, Client entity) {
        if (dto == null || entity == null) return;
        entity.setType(dto.getType());
        entity.setNom(dto.getNom());
        entity.setCin(dto.getCin());
        entity.setIce(dto.getIce());
        entity.setAdresse(dto.getAdresse());
        entity.setVille(dto.getVille());
        entity.setVoiture(dto.getVoiture());
        entity.setImmatriculation(dto.getImmatriculation());
        entity.setnPermis(dto.getnPermis());
        entity.setTel(dto.getTel());
    }
}
