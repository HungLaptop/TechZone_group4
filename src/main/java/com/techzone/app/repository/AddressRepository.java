package com.techzone.app.repository;

import com.techzone.app.entity.Address;
import com.techzone.app.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(AppUser user);
}