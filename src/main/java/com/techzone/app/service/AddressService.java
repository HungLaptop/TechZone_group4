package com.techzone.app.service;

import com.techzone.app.entity.Address;
import com.techzone.app.entity.AppUser;

import java.util.List;

public interface AddressService {
    List<Address> listByUser(AppUser user);
    Address save(Address address);
    void delete(Long id);
    Address setDefaultAddress(AppUser user, Long id);
}