package com.skyNet.dto.dashboard;

import java.time.LocalDateTime;

public record CriticalAlertDTO(Long alertId, Long eventId, String title, String description, String severity,
                               String eventType, String cameraName, String regionName, LocalDateTime detectedAt,
                               Long elapsedTimeSeconds, String status, Boolean requiresHumanReview,
                               Boolean notificationSent, String notificationChannels) {}
