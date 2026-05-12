package com.skyNet.dto.dashboard;

import java.time.LocalDateTime;

public record DashboardSummaryDTO(Long totalEvents, Long criticalEvents, Long mediumEvents, Long lowEvents,
                                  Long activeCameras, Long inactiveCameras, Double averageConfidence,
                                  Double averageResponseTimeSeconds, String mostDetectedEventType,
                                  String mostCriticalRegion, LocalDateTime lastEventDateTime) {}
