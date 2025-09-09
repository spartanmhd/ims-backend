package com.example.ims_backend.controller;

import com.example.ims_backend.entity.Achat;
import com.example.ims_backend.service.AchatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/achats")
@CrossOrigin(origins = "http://localhost:4200")
public class AchatController {

    @Autowired
    private AchatService achatService;

    @PostMapping
    public ResponseEntity<?> createAchat(@Valid @RequestBody Achat achat) {
        try {
            Achat saved = achatService.saveAchat(achat);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            // Return validation error as JSON
            return ResponseEntity.badRequest().body(
                    java.util.Collections.singletonMap("avance", e.getMessage())
            );
        }
    }

    @GetMapping
    public Page<Achat> getAchats(
            @RequestParam(required = false) String fournisseurName,
            @RequestParam(required = false) String origineName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (fournisseurName != null && origineName != null) {
            return achatService.findAchatsByFournisseurAndOrigineName(fournisseurName, origineName, pageable);
        } else {
            return achatService.findAll(pageable);
        }
    }

    @GetMapping("/search/date")
    public Page<Achat> searchByDateAchat(@RequestParam String dateAchat,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        LocalDate date = LocalDate.parse(dateAchat);
        Pageable pageable = PageRequest.of(page, size);
        return achatService.searchByDateAchat(date, pageable);
    }

    @GetMapping("/search/bl")
    public Page<Achat> searchByNumeroBL(@RequestParam String numeroBL,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return achatService.searchByNumeroBL(numeroBL, pageable);
    }

    @GetMapping("/newest")
    public Page<Achat> getAchatsByDateDesc(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return achatService.getAchatsByDateDesc(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Achat> getAchatById(@PathVariable Integer id) {
        return achatService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/facture")
    public ResponseEntity<byte[]> downloadFacturePdf(@PathVariable Integer id) {
        byte[] pdfBytes = achatService.generateFacturePdf(id); // implement this method
        if (pdfBytes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=facture_achat_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAchat(@PathVariable Integer id, @Valid @RequestBody Achat achat) {
        try {
            Optional<Achat> updated = achatService.updateAchat(id, achat);
            return updated.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    java.util.Collections.singletonMap("avance", e.getMessage())
            );
        }
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