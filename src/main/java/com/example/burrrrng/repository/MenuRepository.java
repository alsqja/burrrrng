package com.example.burrrrng.repository;

import com.example.burrrrng.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.burrrrng.entity.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByStoreAndName(Store store, String name);

    List<Menu> findByStoreId(Long storeId);

    Optional<Menu> findById(Long id);

    @Query("SELECT m FROM Menu m WHERE m.store = :store AND m.id = :id")
    Optional<Menu> findByStoreAndId(@Param("store") Store store, @Param("id") Long id);
}
