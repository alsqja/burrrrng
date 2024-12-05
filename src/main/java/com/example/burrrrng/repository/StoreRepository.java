package com.example.burrrrng.repository;

import com.example.burrrrng.dto.StoreResDto;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    default Store findByIdOrElseThrow(Long storeId) {
        return findById(storeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 가게 입니다."));
    }

    long countByUserAndStatusNot(User user, StoreStatus status);

    @Query("""
                select new com.example.burrrrng.dto.StoreResDto(
                    s.id,
                    s.name,
                    COALESCE(avg(r.star), 0),
                    s.minPrice,
                    s.openedAt,
                    s.closedAt,
                    s.createdAt,
                    s.updatedAt
                )
                from Store s
                left join Order o on s.id = o.store.id
                left join Review r on r.order.id = o.id
                group by s.id
            """)
    List<StoreResDto> findAllStoresWithStar();

    List<Store> findByUserId(Long id);

}
