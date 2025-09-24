package com.example.ims_backend.repository;

import com.example.ims_backend.entity.AchatOrigine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchatOrigineRepository extends JpaRepository<AchatOrigine, Integer> {
    List<AchatOrigine> findByAchat_IdAchat(Integer idAchat);
    void deleteAllByAchat_IdAchat(Integer idAchat);
    AchatOrigine findTopByOrigine_IdOrigineOrderByAchat_DateAchatDescAchat_IdAchatDesc(Integer origineId);

}
