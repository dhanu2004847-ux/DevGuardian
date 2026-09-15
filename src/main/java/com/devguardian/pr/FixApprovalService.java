package com.devguardian.pr;
import org.springframework.stereotype.Service;
@Service
public class FixApprovalService {
    public boolean isApproved(PullRequestRecord record) { return true; }
}