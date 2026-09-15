package com.devguardian.report;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;
    public ReportController(ReportService reportService) { this.reportService = reportService; }

    @GetMapping("/{repoId}")
    public ResponseEntity<Report> getReport(@PathVariable Long repoId) {
        return ResponseEntity.ok(reportService.getReport(repoId));
    }

    @PostMapping("/{repoId}/generate")
    public ResponseEntity<Report> generate(@PathVariable Long repoId) {
        return ResponseEntity.ok(reportService.generateReport(repoId));
    }

    @GetMapping("/{repoId}/export")
    public ResponseEntity<byte[]> exportPDF(@PathVariable Long repoId) {
        return ResponseEntity.ok(reportService.exportPDF(repoId));
    }
}