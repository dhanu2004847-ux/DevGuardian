package com.devguardian.scanner;

import java.util.List;

public interface ScannerAdapter {
    /** repoId identifies which repo to scan; scanResult is the parent record findings attach to. */
    List<Finding> scan(Long repoId, ScanResult scanResult);
}
