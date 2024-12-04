package com.example.burrrrng.service;

import com.example.burrrrng.dto.CreateReviewReqDto;
import com.example.burrrrng.dto.ListReviewResDto;
import com.example.burrrrng.dto.ReviewResDto;
import com.example.burrrrng.entity.Order;
import com.example.burrrrng.entity.Review;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.repository.OrderRepository;
import com.example.burrrrng.repository.ReviewRepository;
import com.example.burrrrng.repository.StoreRepository;
import com.example.burrrrng.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Override
    public ReviewResDto save(Long orderId, Long userId, CreateReviewReqDto dto) {
        Order order = orderRepository.findByIdOrElseThrow(orderId);
        User user = userRepository.findByIdOrElseThrow(userId);

        if (user.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "탈퇴된 유저 입니다.");
        }

        Review saveReview = new Review(order, user, dto.getStar(), dto.getComment());

        return new ReviewResDto(reviewRepository.save(saveReview));
    }

    @Override
    public ListReviewResDto findAllStoreReviews(Long storeId) {
        Store store = storeRepository.findByIdOrElseThrow(storeId);

        List<ReviewResDto> reviews = reviewRepository.findAllStoreReviews(storeId);

        return new ListReviewResDto(reviews);
    }
}
