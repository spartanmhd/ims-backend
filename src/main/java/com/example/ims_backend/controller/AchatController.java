package com.example.ims_backend.controller;

import com.example.ims_backend.dto.AchatDTO;
import com.example.ims_backend.dto.AchatOrigineDTO;
import com.example.ims_backend.dto.AchatRequestDTO;
import com.example.ims_backend.mapper.AchatMapper;
import com.example.ims_backend.service.AchatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/achats")
@CrossOrigin(origins = "http://localhost:4200")
public class AchatController {

    @Autowired
    private AchatService achatService;

    @Autowired
    private AchatMapper achatMapper;

    @PostMapping
    public ResponseEntity<?> createAchat(@Valid @RequestBody AchatRequestDTO request) {
        try {
            var achat = achatMapper.toEntity(request);
            var saved = achatService.saveAchatWithOrigines(achat, request.getOrigines());
            var origines = achatService.getAchatOriginesForAchat(saved.getIdAchat());
            var dto = achatMapper.toDTO(saved, origines);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("error", e.getMessage())
            );
        }
    }

    @GetMapping("/simple")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAchat(
            @PathVariable Integer id,
            @Valid @RequestBody AchatRequestDTO request) {
        try {
            var achat = achatMapper.toEntity(request);
            Optional<com.example.ims_backend.entity.Achat> updated = achatService.updateAchatWithOrigines(id, achat, request.getOrigines());
            return updated
                    .map(a -> {
                        var origines = achatService.getAchatOriginesForAchat(a.getIdAchat());
                        AchatDTO dto = achatMapper.toDTO(a, origines);
                        return ResponseEntity.ok(dto);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("error", e.getMessage())
            );
        }
    }

    @GetMapping
    public ResponseEntity<Page<AchatDTO>> getAchats(
            @RequestParam(required = false) String fournisseurName,
            @RequestParam(required = false) String origineName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<com.example.ims_backend.entity.Achat> achats;
        if (fournisseurName != null && origineName != null) {
            achats = achatService.findAchatsByFournisseurAndOrigineName(fournisseurName, origineName, pageable);
        } else {
            achats = achatService.findAll(pageable);
        }
        // Always map to DTO, never return entity directly
        Page<AchatDTO> achatDTOs = achats.map(achat -> {
            var origines = achatService.getAchatOriginesForAchat(achat.getIdAchat());
            return achatMapper.toDTO(achat, origines);
        });
        return ResponseEntity.ok(achatDTOs);
    }

    @GetMapping("/search/date")
    public ResponseEntity<Page<AchatDTO>> searchByDateAchat(@RequestParam String dateAchat,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        LocalDate date = LocalDate.parse(dateAchat);
        Pageable pageable = PageRequest.of(page, size);
        Page<com.example.ims_backend.entity.Achat> achats = achatService.searchByDateAchat(date, pageable);
        Page<AchatDTO> achatDTOs = achats.map(achat -> {
            var origines = achatService.getAchatOriginesForAchat(achat.getIdAchat());
            return achatMapper.toDTO(achat, origines);
        });
        return ResponseEntity.ok(achatDTOs);
    }

    @GetMapping("/search/bl")
    public ResponseEntity<Page<AchatDTO>> searchByNumeroBL(@RequestParam String numeroBL,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<com.example.ims_backend.entity.Achat> achats = achatService.searchByNumeroBL(numeroBL, pageable);
        Page<AchatDTO> achatDTOs = achats.map(achat -> {
            var origines = achatService.getAchatOriginesForAchat(achat.getIdAchat());
            return achatMapper.toDTO(achat, origines);
        });
        return ResponseEntity.ok(achatDTOs);
    }

    @GetMapping("/newest")
    public ResponseEntity<Page<AchatDTO>> getAchatsByDateDesc(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<com.example.ims_backend.entity.Achat> achats = achatService.getAchatsByDateDesc(pageable);
        Page<AchatDTO> achatDTOs = achats.map(achat -> {
            var origines = achatService.getAchatOriginesForAchat(achat.getIdAchat());
            return achatMapper.toDTO(achat, origines);
        });
        return ResponseEntity.ok(achatDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatDTO> getAchatById(@PathVariable Integer id) {
        return achatService.findById(id)
                .map(achat -> {
                    var origines = achatService.getAchatOriginesForAchat(achat.getIdAchat());
                    AchatDTO dto = achatMapper.toDTO(achat, origines);
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/origines")
    public ResponseEntity<List<AchatOrigineDTO>> getAchatOrigines(@PathVariable Integer id) {
        List<AchatOrigineDTO> origineDTOs = achatService.getAchatOriginesForAchat(id)
                .stream()
                .map(achatMapper::origineToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(origineDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAchat(@PathVariable Integer id) {
        boolean deleted = achatService.deleteAchat(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}