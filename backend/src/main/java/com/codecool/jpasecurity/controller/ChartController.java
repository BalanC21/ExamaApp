package com.codecool.jpasecurity.controller;

import com.codecool.jpasecurity.dto.CatChart;
import com.codecool.jpasecurity.dto.NeedlePieRevenueDTO;
import com.codecool.jpasecurity.dto.ToolTipDTO;
import com.codecool.jpasecurity.service.ChartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/charts")
public class ChartController {
    private final ChartService chartService;


    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping
    public ResponseEntity<List<CatChart>> getPieChartData() {
        return ResponseEntity.ok(chartService.getPieChartDataForUser());
    }

    @GetMapping("/needle-pie")
    public ResponseEntity<List<NeedlePieRevenueDTO>> getNeedlePieRevenue() {
        return ResponseEntity.ok(chartService.getNeedlePieRevenueDTO());
    }

    @GetMapping("/tooltip")
    public ResponseEntity<List<ToolTipDTO>> getAllToolTipDataForUser() {
        return ResponseEntity.ok(chartService.getToolTipData());
    }
}
