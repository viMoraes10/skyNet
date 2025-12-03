package com.skyNet.repository;

import com.skyNet.model.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    @Query("SELECT o FROM Occurrence o WHERE o.date BETWEEN :dataInicial AND :dataFinal")
    List<Occurrence> buscarOcorrenciasPorPeriodo(@Param("dataInicial") LocalDate dataInicial,
                                                 @Param("dataFinal") LocalDate dataFinal);

    @Query("SELECT o FROM Occurrence o WHERE LOWER(o.description) = LOWER(:motivo)")
    List<Occurrence> buscarOcorrenciasPorMotivo(@Param("motivo") String motivo);

    @Query("SELECT o FROM Occurrence o WHERE LOWER(o.description) LIKE LOWER(CONCAT('%', :trechoMotivo, '%'))")
    List<Occurrence> buscarOcorrenciasPorMotivoParcial(@Param("trechoMotivo") String trechoMotivo);

    long countByDate(LocalDate date);

    @Query("SELECT AVG(o.reliable) FROM Occurrence o")
    Double calcularMediaConfiabilidade();

}
