package com.devguardian.pr;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pr")
public class PRController {
    private final PRService prService;
    public PRController(PRService prService) { this.prService = prService; }

    @PostMapping("/propose/{findingId}")
    public ResponseEntity<PullRequestRecord> propose(@PathVariable Long findingId) {
        return ResponseEntity.ok(prService.proposeFix(findingId));
    }

    @PostMapping("/{prRecordId}/approve")
    public ResponseEntity<Void> approve(@PathVariable Long prRecordId) {
        prService.approveFix(prRecordId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{prRecordId}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long prRecordId) {
        prService.rejectFix(prRecordId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{repoId}")
    public ResponseEntity<?> listPRs(@PathVariable Long repoId) {
        return ResponseEntity.ok(prService.listPRs(repoId));
    }
}