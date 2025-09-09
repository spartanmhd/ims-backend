package com.example.ims_backend.service;

import com.example.ims_backend.entity.Achat;
import com.example.ims_backend.entity.Origine;
import com.example.ims_backend.repository.AchatRepository;
import com.example.ims_backend.repository.OrigineRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AchatService {

    @Autowired
    private AchatRepository achatRepository;

    @Autowired
    private OrigineRepository origineRepository;

    public Achat saveAchat(Achat achat) {
        // Conditional validation for 'avance'
        if (!"Chèque".equalsIgnoreCase(achat.getModePaiement()) && achat.getAvance() == null) {
            throw new IllegalArgumentException("L'avance est obligatoire");
        }

        if (achat.getOrigine() != null && achat.getOrigine().getNom() != null) {
            // Fetch Origine by name (unique constraint on nom required!)
            Origine origine = origineRepository.findByNom(achat.getOrigine().getNom())
                    .orElseThrow(() -> new IllegalArgumentException("Origine not found"));

            // Set correct references
            achat.setOrigine(origine);
            achat.setFournisseur(origine.getFournisseur());

            // Save Achat first
            Achat savedAchat = achatRepository.save(achat);

            // Update Origine stock with weighted average price
            Double oldQty = origine.getQuantite() != null ? origine.getQuantite().doubleValue() : 0.0;
            Double oldPrice = origine.getPrixAchat() != null ? origine.getPrixAchat().doubleValue() : 0.0;
            Double addQty = achat.getQuantite() != null ? achat.getQuantite().doubleValue() : 0.0;
            Double addPrice = achat.getPrixAchat() != null ? achat.getPrixAchat().doubleValue() : 0.0;

            Double totalQty = oldQty + addQty;
            Double weightedPrice = totalQty > 0
                    ? ((oldQty * oldPrice) + (addQty * addPrice)) / totalQty
                    : 0.0;

            origine.setQuantite(BigDecimal.valueOf(totalQty));
            origine.setPrixAchat(BigDecimal.valueOf(weightedPrice));

            origineRepository.save(origine);

            return savedAchat;
        } else {
            throw new IllegalArgumentException("Origine name must be provided for Achat!");
        }
    }

    // Search Achat by Fournisseur name and Origine name
    public Page<Achat> findAchatsByFournisseurAndOrigineName(String fournisseurName, String origineName, Pageable pageable) {
        return achatRepository.findByFournisseurNameAndOrigineNameOrderByDateAchatDesc(fournisseurName, origineName, pageable);
    }

    // Get all Achats with joins
    public Page<Achat> findAll(Pageable pageable) {
        return achatRepository.findAllByOrderByDateAchatDesc(pageable);
    }

    public Optional<Achat> findById(Integer id) {
        return achatRepository.findById(id);
    }

    // Update Achat; always use Origine's Fournisseur as the Achat's Fournisseur
    public Optional<Achat> updateAchat(Integer id, Achat updatedAchat) {
        // Conditional validation for 'avance' on update
        if (!"Chèque".equalsIgnoreCase(updatedAchat.getModePaiement()) && updatedAchat.getAvance() == null) {
            throw new IllegalArgumentException("L'avance est obligatoire");
        }

        return achatRepository.findById(id).map(existingAchat -> {
            existingAchat.setPrixAchat(updatedAchat.getPrixAchat());
            existingAchat.setQuantite(updatedAchat.getQuantite());
            existingAchat.setDateAchat(updatedAchat.getDateAchat());
            existingAchat.setNumeroBL(updatedAchat.getNumeroBL());
            existingAchat.setModePaiement(updatedAchat.getModePaiement());
            existingAchat.setNumeroCheque(updatedAchat.getNumeroCheque());
            existingAchat.setDatePaiement(updatedAchat.getDatePaiement());
            existingAchat.setAvance(updatedAchat.getAvance());
            // Always set fournisseur from origine!
            if (updatedAchat.getOrigine() != null && updatedAchat.getOrigine().getNom() != null) {
                Origine origine = origineRepository.findByNom(updatedAchat.getOrigine().getNom())
                        .orElseThrow(() -> new IllegalArgumentException("Origine not found"));
                existingAchat.setOrigine(origine);
                existingAchat.setFournisseur(origine.getFournisseur());
            } else {
                throw new IllegalArgumentException("Origine name must be provided for Achat update!");
            }
            return achatRepository.save(existingAchat);
        });
    }

    public boolean deleteAchat(Integer id) {
        return achatRepository.findById(id).map(a -> {
            achatRepository.delete(a);
            return true;
        }).orElse(false);
    }

    public Page<Achat> searchByDateAchat(java.time.LocalDate dateAchat, Pageable pageable) {
        return achatRepository.findByDateAchat(dateAchat, pageable);
    }

    public Page<Achat> searchByNumeroBL(String numeroBL, Pageable pageable) {
        return achatRepository.findByNumeroBLContainingIgnoreCase(numeroBL, pageable);
    }

    public Page<Achat> getAchatsByDateDesc(Pageable pageable) {
        return achatRepository.findAllByOrderByDateAchatDesc(pageable);
    }

    public byte[] generateFacturePdf(Integer id) {
        Optional<Achat> achatOpt = findById(id);
        if (!achatOpt.isPresent()) {
            return null;
        }
        Achat achat = achatOpt.get();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Facturation Achat", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); // Empty line for spacing

            // Table for Achat details
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);

            // Utility function for table rows
            table.addCell("ID Achat:");
            table.addCell(String.valueOf(achat.getIdAchat()));

            table.addCell("Origine:");
            table.addCell(achat.getOrigine() != null ? achat.getOrigine().getNom() : "");

            table.addCell("Fournisseur:");
            table.addCell(achat.getFournisseur() != null ? achat.getFournisseur().getName() : "");

            table.addCell("Date Achat:");
            table.addCell(String.valueOf(achat.getDateAchat()));

            table.addCell("Quantité:");
            table.addCell(String.valueOf(achat.getQuantite()));

            table.addCell("Prix Achat HT:");
            table.addCell(String.valueOf(achat.getPrixAchatHT()));

            table.addCell("Prix Achat TTC:");
            table.addCell(String.valueOf(achat.getPrixAchat()));

            table.addCell("Montant Total:");
            table.addCell(String.valueOf(achat.getTotalPrixTTC()));

            table.addCell("Numéro BL:");
            table.addCell(String.valueOf(achat.getNumeroBL()));

            table.addCell("Mode Paiement:");
            table.addCell(String.valueOf(achat.getModePaiement()));

            table.addCell("Date Paiement:");
            table.addCell(String.valueOf(achat.getDatePaiement()));

            table.addCell("Numéro Chèque:");
            table.addCell(String.valueOf(achat.getNumeroCheque()));

            table.addCell("Avance:");
            table.addCell(String.valueOf(achat.getAvance()));

            table.addCell("Restant à payer:");
            table.addCell(String.valueOf(achat.getRestToPay()));

            document.add(table);

            document.add(new Paragraph(" ")); // Extra space

            // Footer
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
            Paragraph footer = new Paragraph("Généré le: " + java.time.LocalDateTime.now(), footerFont);
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}