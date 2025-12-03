package com.skyNet.controller;

import com.skyNet.dto.OccurrenceDTO;
import com.skyNet.service.OccurrenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value ="occurrence")
@SecurityRequirement(name = "bearerAuth")
public class OccurrenceController {

    OccurrenceService occurrenceService;

    public OccurrenceController(OccurrenceService occurrenceService) {
        this.occurrenceService = occurrenceService;
    }

    @GetMapping
    @Operation(summary = "Listar ocorrências", description = "Retorna todas as ocorrências registradas")
    public List<OccurrenceDTO> getAllOccurrences() {
        return occurrenceService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ocorrência por ID", description = "Localiza uma ocorrência específica pelo seu identificador")
    public OccurrenceDTO getOccurrenceById(@PathVariable Long id) {
        return occurrenceService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Criar ocorrência", description = "Registra uma nova ocorrência")
    public ResponseEntity<OccurrenceDTO> createOccurrence(@RequestBody OccurrenceDTO dto) {
        OccurrenceDTO created = occurrenceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar ocorrência", description = "Atualiza os dados de uma ocorrência existente")
    public OccurrenceDTO updateOccurrence(@PathVariable Long id, @RequestBody OccurrenceDTO dto) {
        return occurrenceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir ocorrência", description = "Remove uma ocorrência pelo seu identificador")
    public ResponseEntity<Void> deleteOccurrence(@PathVariable Long id) {
        occurrenceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/periodo")
    @Operation(summary = "Buscar ocorrências por período", description = "Retorna todas as ocorrências entre as datas informadas (formato ISO, ex: 2024-01-31)")
    public List<OccurrenceDTO> getOccurrencesByPeriod(
            @RequestParam("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        return occurrenceService.findByPeriod(dataInicial, dataFinal);
    }

    @GetMapping("/motivo")
    @Operation(summary = "Buscar ocorrências por motivo", description = "Retorna ocorrências com a descrição exatamente igual ao motivo informado, ignorando maiúsculas e minúsculas")
    public List<OccurrenceDTO> getOccurrencesByMotivo(@RequestParam("motivo") String motivo) {
        return occurrenceService.findByMotivo(motivo);
    }

    @GetMapping("/motivo/parcial")
    @Operation(summary = "Buscar ocorrências por motivo parcial", description = "Retorna ocorrências cuja descrição contenha o trecho informado, ignorando maiúsculas e minúsculas")
    public List<OccurrenceDTO> getOccurrencesByMotivoParcial(@RequestParam("trechoMotivo") String trechoMotivo) {
        return occurrenceService.findByMotivoParcial(trechoMotivo);
    }
}
