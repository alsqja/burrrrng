package com.example.burrrrng.repository;

import com.example.burrrrng.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerStoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByIdAndUserId(Long storeId, Long userId);
}
