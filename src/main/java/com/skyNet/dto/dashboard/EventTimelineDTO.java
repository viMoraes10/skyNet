package com.skyNet.dto.dashboard;

public record EventTimelineDTO(String period, Long totalEvents, Long criticalEvents, Long mediumEvents, Long lowEvents) {}
