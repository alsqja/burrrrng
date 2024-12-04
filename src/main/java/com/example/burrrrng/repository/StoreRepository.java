package com.example.burrrrng.repository;

import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface StoreRepository extends JpaRepository<Store, Long> {

    default Store findByIdOrElseThrow(Long storeId) {
        return findById(storeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 가게 입니다."));
    }

    long countByUserAndStatusNot(User user, StoreStatus status);
}
