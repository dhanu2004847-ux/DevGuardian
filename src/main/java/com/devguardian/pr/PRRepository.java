package com.devguardian.pr;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PRRepository extends JpaRepository<PullRequestRecord, Long> {}