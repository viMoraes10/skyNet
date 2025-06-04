package com.skyNet.controller;

import com.skyNet.dto.OccurrenceDTO;
import com.skyNet.service.OccurrenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="occurrence")
public class OccurrenceController {

    OccurrenceService occurrenceService;

    public OccurrenceController(OccurrenceService occurrenceService) {
        this.occurrenceService = occurrenceService;
    }

    @GetMapping
    public List<OccurrenceDTO> getAllOccurrences() {
        return occurrenceService.findAll();
    }

    @GetMapping("/{id}")
    public OccurrenceDTO getOccurrenceById(Long id) {
        return occurrenceService.findById(id);
    }

    @PostMapping
    public ResponseEntity<OccurrenceDTO> createOccurrence(@RequestBody OccurrenceDTO dto) {
        OccurrenceDTO created = occurrenceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public OccurrenceDTO updateOccurrence(@PathVariable Long id, @RequestBody OccurrenceDTO dto) {
        return occurrenceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOccurrence(@PathVariable Long id) {
        occurrenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
