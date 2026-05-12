package com.skyNet.dto.dashboard;

import java.time.LocalDateTime;

public record CameraStatusDTO(Long cameraId, String cameraName, String regionName, String status,
                              LocalDateTime lastFrameAt, LocalDateTime lastEventAt, Long totalEventsToday,
                              Long criticalEventsToday, Double averageConfidenceToday,
                              String streamIdentifier, Double healthScore) {}
