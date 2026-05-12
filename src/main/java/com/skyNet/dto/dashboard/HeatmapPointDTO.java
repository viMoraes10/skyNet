package com.skyNet.dto.dashboard;

public record HeatmapPointDTO(Double latitude, Double longitude, Double weight, Long totalEvents,
                              Long criticalEvents, Long regionId, String regionName, Long cameraId,
                              String cameraName) {}
