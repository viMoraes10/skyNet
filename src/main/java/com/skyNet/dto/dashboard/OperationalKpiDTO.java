package com.skyNet.dto.dashboard;

public record OperationalKpiDTO(Long totalProcessedFrames, Long totalDetectedEvents, Double detectionRate,
                                Double averageInferenceLatencyMs, Double averageConfidence, Double falsePositiveRate,
                                Double humanReviewRate, Double criticalAlertResolutionAverageSeconds,
                                Long notificationsSent, String modelVersion, String modelHash) {}
