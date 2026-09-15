package com.devguardian.ai;
import org.springframework.stereotype.Service;
@Service
public class AIExplanationService {
    private final GeminiClientService geminiClient;
    public AIExplanationService(GeminiClientService geminiClient) { this.geminiClient = geminiClient; }
    public AIResponseDTO explainFinding(Long findingId) {
        return new AIResponseDTO(geminiClient.callGemini("Explain finding"));
    }
}