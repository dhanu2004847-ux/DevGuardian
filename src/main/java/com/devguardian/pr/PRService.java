package com.devguardian.pr;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PRService {
    private final PRRepository prRepository;
    private final GitHubPRClient gitHubPRClient;
    private final FixApprovalService approvalService;

    public PRService(PRRepository prRepository, GitHubPRClient gitHubPRClient, FixApprovalService approvalService) {
        this.prRepository = prRepository;
        this.gitHubPRClient = gitHubPRClient;
        this.approvalService = approvalService;
    }

    public PullRequestRecord proposeFix(Long findingId) {
        PullRequestRecord record = new PullRequestRecord();
        record.setFindingId(findingId);
        return prRepository.save(record);
    }

    public void approveFix(Long prRecordId) {
        PullRequestRecord record = prRepository.findById(prRecordId).orElseThrow();
        if (approvalService.isApproved(record)) {
            gitHubPRClient.createPR(record);
        }
    }

    public void rejectFix(Long prRecordId) { prRepository.deleteById(prRecordId); }
    public List<PullRequestRecord> listPRs(Long repoId) { return prRepository.findAll(); }
}