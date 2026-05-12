package com.skyNet.dto.dashboard;

import java.time.LocalDateTime;

public record RecentEventDTO(Long id, String eventType, String eventTypeLabel, String severity, Double confidence,
                             Long cameraId, String cameraName, Long regionId, String regionName,
                             LocalDateTime detectedAt, String status, Boolean reviewedByHuman,
                             String snapshotUrl, String anonymizedSnapshotUrl) {}
