package com.devguardian.ai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class ChatController {
    private final GeminiClientService geminiClientService;

    public ChatController(GeminiClientService geminiClientService) {
        this.geminiClientService = geminiClientService;
    }

    @PostMapping("/chat")
    public ResponseEntity<AIResponseDTO> chat(@RequestBody String prompt) {
        return ResponseEntity.ok(new AIResponseDTO(geminiClientService.callGemini(prompt)));
    }

    @GetMapping("/chat/history/{repoId}")
    public ResponseEntity<?> history(@PathVariable Long repoId) {
        return ResponseEntity.ok().build();
    }
}