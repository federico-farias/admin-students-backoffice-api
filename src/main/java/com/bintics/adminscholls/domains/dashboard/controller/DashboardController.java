package com.bintics.adminscholls.domains.dashboard.controller;

import com.bintics.adminscholls.domains.dashboard.dto.DashboardStatsDTO;
import com.bintics.adminscholls.domains.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        DashboardStatsDTO stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/stats/student-occupancy")
    public ResponseEntity<Double> getStudentOccupancyRate() {
        Double rate = dashboardService.getStudentOccupancyRate();
        return ResponseEntity.ok(rate);
    }
    
    @GetMapping("/stats/payment-success")
    public ResponseEntity<Double> getPaymentSuccessRate() {
        Double rate = dashboardService.getPaymentSuccessRate();
        return ResponseEntity.ok(rate);
    }
    
    @GetMapping("/stats/group-utilization")
    public ResponseEntity<Double> getGroupUtilizationRate() {
        Double rate = dashboardService.getGroupUtilizationRate();
        return ResponseEntity.ok(rate);
    }
}
