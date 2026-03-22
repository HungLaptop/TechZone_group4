package com.techzone.app.service.impl;

import com.techzone.app.entity.Address;
import com.techzone.app.entity.AppUser;
import com.techzone.app.repository.AddressRepository;
import com.techzone.app.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<Address> listByUser(AppUser user) {
        return addressRepository.findByUser(user);
    }

    @Override
    public Address save(Address address) {
        if (address.isDefault()) {
            addressRepository.findByUser(address.getUser()).forEach(a -> {
                a.setDefault(false);
                addressRepository.save(a);
            });
        }
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Address setDefaultAddress(AppUser user, Long id) {
        addressRepository.findByUser(user).forEach(a -> {
            a.setDefault(a.getId().equals(id));
            addressRepository.save(a);
        });
        return addressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Address not found"));
    }
}
