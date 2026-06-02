package com.skyNet.dto.dashboard;

public record RegionRiskDTO(Long regionId, String regionName, Long totalEvents, Long criticalEvents,
                            Double averageConfidence, Double riskScore, String riskLevel,
                            String mostCommonEventType) {}
