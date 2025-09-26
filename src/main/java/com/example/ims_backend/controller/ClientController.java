package com.example.ims_backend.controller;

import com.example.ims_backend.dto.ClientDTO;
import com.example.ims_backend.entity.Client;
import com.example.ims_backend.mapper.ClientMapper;
import com.example.ims_backend.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<ClientDTO> getAll() {
        return clientService.getAll().stream().map(ClientMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/search")
    public Page<ClientDTO> search(@RequestParam String nom, Pageable pageable) {
        return clientService.searchByNom(nom, pageable).map(ClientMapper::toDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Integer id) {
        return clientService.getById(id)
                .map(c -> ResponseEntity.ok(ClientMapper.toDto(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ClientDTO create(@Valid @RequestBody ClientDTO dto) {
        Client saved = clientService.create(dto);
        return ClientMapper.toDto(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Integer id, @Valid @RequestBody ClientDTO dto) {
        return clientService.update(id, dto)
                .map(c -> ResponseEntity.ok(ClientMapper.toDto(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (clientService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
