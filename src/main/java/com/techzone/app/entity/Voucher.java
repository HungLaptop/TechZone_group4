package com.techzone.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private double discountPercent;
    private double discountValue;
    private boolean active;
    private LocalDate startDate;
    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser assignedTo;
}