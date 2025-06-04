package com.skyNet.dto;

import java.time.LocalDate;

public record OccurrenceDTO(
        Long id,
        String description,
        Double reliable,
        LocalDate date,
        Long userId
) {
}
