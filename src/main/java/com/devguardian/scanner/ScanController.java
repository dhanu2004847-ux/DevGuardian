package com.devguardian.scanner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ScanController {
    private final ScanOrchestratorService orchestratorService;
    public ScanController(ScanOrchestratorService orchestratorService) { this.orchestratorService = orchestratorService; }

    @PostMapping("/api/scans/start/{repoId}")
    public ResponseEntity<ScanResult> startScan(@PathVariable Long repoId) {
        return ResponseEntity.ok(orchestratorService.runScans(repoId));
    }

    @GetMapping("/api/scans/{scanId}")
    public ResponseEntity<ScanResult> getScan(@PathVariable Long scanId) {
        return ResponseEntity.ok(orchestratorService.getScanResult(scanId));
    }

    @GetMapping("/api/scans/{scanId}/findings")
    public ResponseEntity<List<Finding>> getFindings(@PathVariable Long scanId) {
        return ResponseEntity.ok(orchestratorService.getFindings(scanId));
    }

    @GetMapping("/api/repos/{repoId}/scans")
    public ResponseEntity<List<ScanResult>> getScanHistory(@PathVariable Long repoId) {
        return ResponseEntity.ok(orchestratorService.getScanHistory(repoId));
    }
}
