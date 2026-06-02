package com.skyNet.dto.dashboard;

import java.time.LocalDateTime;

public record DashboardFilterDTO(LocalDateTime startDate, LocalDateTime endDate, Long cameraId, Long regionId,
                                 String eventType, String severity) {}
