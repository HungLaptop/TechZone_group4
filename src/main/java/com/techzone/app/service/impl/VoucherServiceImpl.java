package com.techzone.app.service.impl;

import com.techzone.app.entity.AppUser;
import com.techzone.app.entity.Voucher;
import com.techzone.app.repository.VoucherRepository;
import com.techzone.app.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    @Override
    public List<Voucher> listAvailable() {
        LocalDate now = LocalDate.now();
        return voucherRepository.findByActiveTrue().stream()
                .filter(v -> v.getStartDate() == null || !now.isBefore(v.getStartDate()))
                .filter(v -> v.getExpiryDate() == null || !now.isAfter(v.getExpiryDate()))
                .toList();
    }

    @Override
    public List<Voucher> listForUser(AppUser user) {
        return voucherRepository.findByAssignedTo(user);
    }

    @Override
    public Voucher assignToUser(String code, AppUser user) {
        Voucher voucher = voucherRepository.findByCode(code).orElseThrow(() -> new IllegalArgumentException("Voucher not found"));
        voucher.setAssignedTo(user);
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher createVoucher(Voucher voucher) {
        voucher.setActive(true);
        return voucherRepository.save(voucher);
    }

    @Override
    public void deactivate(String code) {
        Voucher voucher = voucherRepository.findByCode(code).orElseThrow(() -> new IllegalArgumentException("Voucher not found"));
        voucher.setActive(false);
        voucherRepository.save(voucher);
    }
}
