package com.example.burrrrng.repository;

import com.example.burrrrng.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
    default Order findByIdOrElseThrow(Long orderId) {
        return findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 주문 입니다."));
    }

    @Query(value = """
                SELECT o.id,
                       s.name AS storeName,
                       o.status AS status,
                       (SELECT m2.name 
                        FROM order_menu om2
                        JOIN menu m2 ON om2.menu_id = m2.id
                        WHERE om2.order_id = o.id
                        ORDER BY m2.price DESC
                        LIMIT 1) AS mainMenu,
                       COUNT(DISTINCT om.menu_id) AS totalMenuCount,
                       SUM(om.amount * m.price) AS totalPrice,
                       o.created_at AS createdAt,
                       o.updated_at AS updatedAt
                FROM `order` o
                JOIN store s ON o.store_id = s.id
                JOIN order_menu om ON om.order_id = o.id
                JOIN menu m ON m.id = om.menu_id
                WHERE o.user_id = :id
                GROUP BY o.id, s.id
            """, nativeQuery = true)
    List<Object[]> findAllUserOrders(Long id);

    Optional<Object> findByStoreIdAndId(Long storeId, Long orderId);

}
