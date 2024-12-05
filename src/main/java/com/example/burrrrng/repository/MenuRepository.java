package com.example.burrrrng.repository;

import com.example.burrrrng.entity.Menu;
import com.example.burrrrng.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByStoreAndName(Store store, String name);

    List<Menu> findByStore(Store store);
}
