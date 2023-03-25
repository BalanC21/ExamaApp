package com.codecool.jpasecurity.controller;

import com.codecool.jpasecurity.dto.RevenueDTO;
import com.codecool.jpasecurity.model.Revenue;
import com.codecool.jpasecurity.service.RevenueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/revenues")
public class RevenueController {

    private final RevenueService revenueService;

    public RevenueController(RevenueService revenueService) {
        this.revenueService = revenueService;
    }

    @GetMapping
    public ResponseEntity<Revenue> getRevenueForUser() {
        return ResponseEntity.ok(revenueService.findRevenueByUser());
    }

    @PostMapping
    public ResponseEntity<RevenueDTO> addRevenue(@Valid @RequestBody RevenueDTO revenueDTO) {
        return ResponseEntity.ok(revenueService.addRevenue(revenueDTO));
    }

    @PatchMapping
    public ResponseEntity<Revenue> updateRevenueAmount(@Valid @RequestBody Revenue revenue) {
        return ResponseEntity.ok(revenueService.modifyRevenue(revenue));
    }
}
