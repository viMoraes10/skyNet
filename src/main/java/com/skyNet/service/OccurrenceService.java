package com.skyNet.service;

import com.skyNet.dto.OccurrenceDTO;
import com.skyNet.model.Occurrence;
import com.skyNet.model.User;
import com.skyNet.repository.OccurrenceRepository;
import com.skyNet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccurrenceService {

    private final OccurrenceRepository occurrenceRepository;
    private final UserRepository userRepository;

    public OccurrenceService(OccurrenceRepository occurrenceRepository, UserRepository userRepository) {
        this.occurrenceRepository = occurrenceRepository;
        this.userRepository = userRepository;
    }

    public List<OccurrenceDTO> findAll() {
        return occurrenceRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public OccurrenceDTO findById(Long id) {
        Occurrence occurrence = occurrenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Occurrence not found"));
        return convertToDTO(occurrence);
    }

    public OccurrenceDTO create(OccurrenceDTO dto) {
        Occurrence occurrence = convertToEntity(dto);
        occurrence = occurrenceRepository.save(occurrence);
        return convertToDTO(occurrence);
    }

    public OccurrenceDTO update(Long id, OccurrenceDTO dto) {
        Occurrence existing = occurrenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Occurrence not found"));

        existing.setDescription(dto.description());
        existing.setReliable(dto.reliable());
        existing.setDate(dto.date());

        if (dto.userId() != null) {
            User user = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existing.setUser(user);
        }

        Occurrence updated = occurrenceRepository.save(existing);
        return convertToDTO(updated);
    }

    public void delete(Long id) {
        if (!occurrenceRepository.existsById(id)) {
            throw new RuntimeException("Occurrence not found");
        }
        occurrenceRepository.deleteById(id);
    }

    private OccurrenceDTO convertToDTO(Occurrence occurrence) {
        return new OccurrenceDTO(
                occurrence.getId(),
                occurrence.getDescription(),
                occurrence.getReliable(),
                occurrence.getDate(),
                occurrence.getUser().getId()
        );
    }

    private Occurrence convertToEntity(OccurrenceDTO dto) {
        Occurrence occurrence = new Occurrence();
        occurrence.setDescription(dto.description());
        occurrence.setReliable(dto.reliable());
        occurrence.setDate(dto.date());

        if (dto.userId() != null) {
            User user = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            occurrence.setUser(user);
        }

        return occurrence;
    }


}
