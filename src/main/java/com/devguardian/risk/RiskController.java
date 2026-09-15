package com.devguardian.risk;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
public class RiskController {
    private final RiskEngineService riskService;
    public RiskController(RiskEngineService riskService) { this.riskService = riskService; }

    @GetMapping("/{repoId}")
    public ResponseEntity<RiskScore> getRisk(@PathVariable Long repoId) {
        return ResponseEntity.ok(riskService.getRiskScore(repoId));
    }

    @GetMapping("/{repoId}/history")
    public ResponseEntity<?> getHistory(@PathVariable Long repoId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{repoId}/recalculate")
    public ResponseEntity<RiskScore> recalculate(@PathVariable Long repoId) {
        return ResponseEntity.ok(riskService.recalculate(repoId));
    }
}