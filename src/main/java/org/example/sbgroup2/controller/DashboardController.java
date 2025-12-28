package org.example.sbgroup2.controller;

import lombok.RequiredArgsConstructor;
import org.example.sbgroup2.services.DashboardService;
import org.example.sbgroup2.services.DashboardSummaryDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3001")
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/summary")
    public DashboardSummaryDTO summary() {
        return service.getSummary();
    }
}

