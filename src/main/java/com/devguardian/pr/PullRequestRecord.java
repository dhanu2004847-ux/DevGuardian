package com.devguardian.pr;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pull_requests")
@Data
public class PullRequestRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long findingId;
}