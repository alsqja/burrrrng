package com.example.burrrrng.repository;

import com.example.burrrrng.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.burrrrng.entity.Store;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByStoreAndName(Store store, String name);

    List<Menu> findByStore(Store store);
    List<Menu> findByStoreId(Long storeId);

    Optional<Menu> findById(Long id);

    @Query("SELECT m FROM Menu m WHERE m.store = :store AND m.id = :id")
    Optional<Menu> findByStoreAndId(@Param("store") Store store, @Param("id") Long id);

    @Query("SELECT m FROM Menu m WHERE m.store.id = :storeId AND m.id = :menuId AND m.user.id = :userId")
    Optional<Menu> findByStoreIdAndMenuIdAndUserId(@Param("storeId") Long storeId,
                                                   @Param("menuId") Long menuId,
                                                   @Param("userId") Long userId);
}
