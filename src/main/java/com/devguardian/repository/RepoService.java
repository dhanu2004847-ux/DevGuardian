package com.devguardian.repository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RepoService {
    private final RepositoryRepository repoRepository;
    private final GitHubClientService gitHubClientService;

    public RepoService(RepositoryRepository repoRepository, GitHubClientService gitHubClientService) {
        this.repoRepository = repoRepository;
        this.gitHubClientService = gitHubClientService;
    }

    public RepoDTO connectRepo(RepoConnectRequestDTO dto) {
        Repository repo = new Repository();
        repo.setName(dto.getName());
        repo.setUrl(dto.getUrl());
        repoRepository.save(repo);
        gitHubClientService.registerWebhook(repo.getUrl());
        return new RepoDTO(repo.getId(), repo.getName(), repo.getUrl());
    }

    public List<Repository> listRepos() { return repoRepository.findAll(); }
    public RepoDTO getRepo(Long id) {
        Repository repo = repoRepository.findById(id).orElseThrow();
        return new RepoDTO(repo.getId(), repo.getName(), repo.getUrl());
    }
    public void disconnect(Long id) { repoRepository.deleteById(id); }
    public void syncRepo(Long id) {}
    public void handleWebhook(String payload) {}
}