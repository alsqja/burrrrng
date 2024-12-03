package com.example.burrrrng.repository;

import com.example.burrrrng.dto.ReviewResDto;
import com.example.burrrrng.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
                select new com.example.burrrrng.dto.ReviewResDto(
                    r.id, r.order.id, r.user.id, r.star, r.comment, r.createdAt, r.updatedAt
                )
                from Review r
                join Store s
                on r.order.store.id = s.id
                where s.id = :storeId
            """)
    List<ReviewResDto> findAllStoreReviews(Long storeId);
}
