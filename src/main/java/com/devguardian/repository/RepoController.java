package com.devguardian.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repos")
public class RepoController {
    private final RepoService repoService;
    public RepoController(RepoService repoService) { this.repoService = repoService; }

    @PostMapping("/connect")
    public ResponseEntity<RepoDTO> connect(@RequestBody RepoConnectRequestDTO dto) {
        return ResponseEntity.ok(repoService.connectRepo(dto));
    }

    @GetMapping
    public ResponseEntity<?> listRepos() { return ResponseEntity.ok(repoService.listRepos()); }

    @GetMapping("/{id}")
    public ResponseEntity<RepoDTO> getRepo(@PathVariable Long id) { return ResponseEntity.ok(repoService.getRepo(id)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disconnect(@PathVariable Long id) {
        repoService.disconnect(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/sync")
    public ResponseEntity<Void> sync(@PathVariable Long id) {
        repoService.syncRepo(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody String payload) {
        repoService.handleWebhook(payload);
        return ResponseEntity.ok().build();
    }
}