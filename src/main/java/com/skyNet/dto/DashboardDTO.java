package com.skyNet.dto;

public record DashboardDTO(
        Long totalUsers,
        Long totalOccurrences,
        Long occurrencesToday,
        Double averageReliability,
        Long totalCameras,
        Long activeCameras,
        Long inactiveCameras
) {
}
