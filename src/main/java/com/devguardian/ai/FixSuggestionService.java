package com.devguardian.ai;
import org.springframework.stereotype.Service;
@Service
public class FixSuggestionService {
    private final GeminiClientService geminiClient;
    public FixSuggestionService(GeminiClientService geminiClient) { this.geminiClient = geminiClient; }
    public AIResponseDTO suggestFix(Long findingId) {
        return new AIResponseDTO(geminiClient.callGemini("Suggest fix"));
    }
}