package com.techzone.app.repository;

import com.techzone.app.entity.Voucher;
import com.techzone.app.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Optional<Voucher> findByCode(String code);
    List<Voucher> findByAssignedTo(AppUser user);
    List<Voucher> findByActiveTrue();
}