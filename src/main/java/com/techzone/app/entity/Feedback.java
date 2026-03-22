package com.techzone.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(length = 1000)
    private String comment;

    private int rating;

    private boolean approved;

    @Column(length = 1000)
    private String reply;

    private LocalDateTime createdAt;
    private LocalDateTime repliedAt;
}