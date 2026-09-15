package com.devguardian.risk;
import org.springframework.stereotype.Component;
@Component
public class SeverityWeightConfig {
    public int getWeight(String severity) { return 10; }
}