package com.devguardian.scanner;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FindingRepository extends JpaRepository<Finding, Long> {
    List<Finding> findByScanResultId(Long scanResultId);
}
