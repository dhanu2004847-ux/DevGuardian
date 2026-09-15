package com.devguardian.repository;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "repositories")
@Data
public class Repository {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
}