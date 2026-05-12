package com.skyNet.service;

import com.skyNet.Enum.dashboard.DashboardGroupBy;
import com.skyNet.dto.dashboard.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface DashboardService {
    DashboardSummaryDTO getSummary(LocalDateTime startDate, LocalDateTime endDate, Long cameraId, Long regionId);
    List<EventTypeChartDTO> getEventsByType(LocalDateTime startDate, LocalDateTime endDate, Long cameraId, Long regionId, String severity);
    List<EventTimelineDTO> getEventsTimeline(LocalDateTime startDate, LocalDateTime endDate, DashboardGroupBy groupBy, Long cameraId, Long regionId, String eventType, String severity);
    List<RecentEventDTO> getRecentEvents(Integer limit, String severity, Long cameraId, Long regionId);
    List<CriticalAlertDTO> getCriticalAlerts(String status, LocalDateTime startDate, LocalDateTime endDate, Integer limit);
    List<CameraStatusDTO> getCameraStatus();
    List<HeatmapPointDTO> getHeatmap(LocalDateTime startDate, LocalDateTime endDate, String eventType, String severity, Long regionId);
    List<RegionRiskDTO> getRegionsRiskRanking(LocalDateTime startDate, LocalDateTime endDate);
    OperationalKpiDTO getOperationalKpis(LocalDateTime startDate, LocalDateTime endDate);
    Map<String, Object> getHumanReview(LocalDateTime startDate, LocalDateTime endDate, Long reviewerId);
    Map<String, Object> getPrivacyCompliance();
    Map<String, Object> getModelPerformance(LocalDateTime startDate, LocalDateTime endDate, String modelVersion);
}
