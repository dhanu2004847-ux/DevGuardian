package com.devguardian.ai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {
    private final AIExplanationService explanationService;
    private final FixSuggestionService fixSuggestionService;

    public AIController(AIExplanationService explanationService, FixSuggestionService fixSuggestionService) {
        this.explanationService = explanationService;
        this.fixSuggestionService = fixSuggestionService;
    }

    @PostMapping("/explain/{findingId}")
    public ResponseEntity<AIResponseDTO> explain(@PathVariable Long findingId) {
        return ResponseEntity.ok(explanationService.explainFinding(findingId));
    }

    @PostMapping("/suggest-fix/{findingId}")
    public ResponseEntity<AIResponseDTO> suggestFix(@PathVariable Long findingId) {
        return ResponseEntity.ok(fixSuggestionService.suggestFix(findingId));
    }
}