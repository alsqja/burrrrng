package com.example.burrrrng.repository;

import com.example.burrrrng.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.burrrrng.entity.Store;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByStoreAndName(Store store, String name);
    Optional<Menu> findByStoreAndId(Store store, Long id);
    List<Menu> findByStoreId(Long storeId);


}
