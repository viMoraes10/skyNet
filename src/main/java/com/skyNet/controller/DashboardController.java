package com.skyNet.controller;

import com.skyNet.Enum.dashboard.DashboardGroupBy;
import com.skyNet.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping({"dashboard", "api/dashboard"})
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Resumo geral")
    public ResponseEntity<?> summary(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                     @RequestParam(required = false) Long cameraId,
                                     @RequestParam(required = false) Long regionId) {
        return ResponseEntity.ok(dashboardService.getSummary(startDate, endDate, cameraId, regionId));
    }

    @GetMapping("/events-by-type")
    public ResponseEntity<?> eventsByType(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                          @RequestParam(required = false) Long cameraId, @RequestParam(required = false) Long regionId, @RequestParam(required = false) String severity) {
        return ResponseEntity.ok(dashboardService.getEventsByType(startDate, endDate, cameraId, regionId, severity));
    }

    @GetMapping("/events-timeline")
    public ResponseEntity<?> timeline(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, @RequestParam(defaultValue = "DAY") DashboardGroupBy groupBy, @RequestParam(required = false) Long cameraId, @RequestParam(required = false) Long regionId, @RequestParam(required = false) String eventType, @RequestParam(required = false) String severity) {
        return ResponseEntity.ok(dashboardService.getEventsTimeline(startDate, endDate, groupBy, cameraId, regionId, eventType, severity));
    }

    @GetMapping("/recent-events")
    public ResponseEntity<?> recent(@RequestParam(defaultValue = "10") Integer limit, @RequestParam(required = false) String severity, @RequestParam(required = false) Long cameraId, @RequestParam(required = false) Long regionId) {
        return ResponseEntity.ok(dashboardService.getRecentEvents(limit, severity, cameraId, regionId));
    }

    @GetMapping("/critical-alerts")
    public ResponseEntity<?> alerts(@RequestParam(required = false) String status, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, @RequestParam(defaultValue = "20") Integer limit) {
        return ResponseEntity.ok(dashboardService.getCriticalAlerts(status, startDate, endDate, limit));
    }

    @GetMapping("/cameras-status")
    public ResponseEntity<?> cameraStatus() {
        return ResponseEntity.ok(dashboardService.getCameraStatus());
    }

    @GetMapping("/heatmap")
    public ResponseEntity<?> heatmap(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, @RequestParam(required = false) String eventType, @RequestParam(required = false) String severity, @RequestParam(required = false) Long regionId) {
        return ResponseEntity.ok(dashboardService.getHeatmap(startDate, endDate, eventType, severity, regionId));
    }

    @GetMapping("/regions-risk-ranking")
    public ResponseEntity<?> ranking(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(dashboardService.getRegionsRiskRanking(startDate, endDate));
    }

    @GetMapping("/operational-kpis")
    public ResponseEntity<?> kpis(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(dashboardService.getOperationalKpis(startDate, endDate));
    }

    @GetMapping("/human-review")
    public ResponseEntity<?> human(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, @RequestParam(required = false) Long reviewerId) {
        return ResponseEntity.ok(dashboardService.getHumanReview(startDate, endDate, reviewerId));
    }

    @GetMapping("/privacy-compliance")
    public ResponseEntity<?> privacy() {
        return ResponseEntity.ok(dashboardService.getPrivacyCompliance());
    }

    @GetMapping("/model-performance")
    public ResponseEntity<?> model(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate, @RequestParam(required = false) String modelVersion) {
        return ResponseEntity.ok(dashboardService.getModelPerformance(startDate, endDate, modelVersion));
    }
}
