package com.phonestore.controller.admin;

import com.phonestore.dto.admin.DashboardResponse;
import com.phonestore.dto.common.ApiResponse;
import com.phonestore.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final DashboardService service;

    public AdminDashboardController(DashboardService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>> dashboard(
            @RequestParam(defaultValue = "5") int topLimit) {
        return ResponseEntity.ok(ApiResponse.success(service.getDashboard(topLimit)));
    }
}
