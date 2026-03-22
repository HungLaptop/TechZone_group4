package com.techzone.app.service;

import com.techzone.app.entity.AppUser;
import com.techzone.app.entity.Voucher;

import java.util.List;

public interface VoucherService {
    List<Voucher> listAvailable();
    List<Voucher> listForUser(AppUser user);
    Voucher assignToUser(String code, AppUser user);
    Voucher createVoucher(Voucher voucher);
    void deactivate(String code);
}