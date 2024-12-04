package com.example.burrrrng.repository;

import com.example.burrrrng.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerStoreRepository extends JpaRepository<Store, Long> {
}
