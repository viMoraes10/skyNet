package com.skyNet.controller;

import com.skyNet.dto.DashboardDTO;
import com.skyNet.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @Operation(summary = "Resumo do dashboard", description = "Retorna dados consolidados para o painel inicial")
    public DashboardDTO getDashboardInfo() {
        return dashboardService.getDashboardInfo();
    }
}
