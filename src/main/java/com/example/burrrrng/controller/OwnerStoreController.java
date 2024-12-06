package com.example.burrrrng.controller;


import com.example.burrrrng.dto.*;
import com.example.burrrrng.dto.common.CommonListResDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.entity.User;
import com.example.burrrrng.service.MenuService;
import com.example.burrrrng.service.OwnerStoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner/stores")
@RequiredArgsConstructor
public class OwnerStoreController {

    private final OwnerStoreService ownerStoreService;
    private MenuService menuService;

    @PostMapping
    public CommonResDto<ResponseOwnerStoreDto> createStore(@RequestBody RequestOwnerStoreDto requestOwnerStoreDto, HttpServletRequest request) {
        return ownerStoreService.createStore(requestOwnerStoreDto, request);
    }

    @GetMapping
    public CommonListResDto<ResponseOwnerStoreDto> viewAllStore(HttpServletRequest request){
        return ownerStoreService.viewAllStore(request);
    }

    @GetMapping("/{id}")
    public CommonListResDto<ResponseOwnerStoreDto> viewOneStore(
            @PathVariable Long id,
            HttpServletRequest request){
        return ownerStoreService.viewOneStore(id, request);
    }

    @GetMapping("/{id}/menus")
    public CommonListResDto<ResponseMenuDto> viewMenus(
            @PathVariable Long id,
            HttpServletRequest request){
        return ownerStoreService.viewMenus(id, request);
    }

    @PatchMapping("/{id}")
    public CommonResDto<ResponseOwnerStoreUpdateDto> updateStore(
            @PathVariable Long id,
            @RequestBody RequestOwnerStoreUpdateDto requestOwnerStoreUpdateDto,
            HttpServletRequest request
    ){
        return ownerStoreService.updateStore(id, requestOwnerStoreUpdateDto, request);
    }

    @PatchMapping("/{storeId}/orders/{orderId}")
    public CommonResDto<ResponseOrderUpdateDto> updateOrder(
            @PathVariable Long storeId,
            @PathVariable Long orderId,
            @RequestBody RequestOrderUpdateDto requestOrderUpdateDto,
            HttpServletRequest request
    ){
        return ownerStoreService.updateOrder(storeId, orderId, requestOrderUpdateDto, request);
    }

    @GetMapping("/{storeId}/orders/{orderId}/menus")
    public ResponseEntity<CommonListResDto<OrderMenuResDto>> findAllStoreOrderMenus(
            @PathVariable Long storeId,
            @PathVariable Long orderId,
            @SessionAttribute User loginUser
    ) {

        return ResponseEntity.ok()
                .body(new CommonListResDto<>(
                        "메뉴 조회 완료",
                        menuService.findAllStoreOrderMenus(storeId, orderId, loginUser.getId()
                        ))
                );
    }

    @GetMapping("/{storeId}/orders")
    public CommonListResDto<ResponseViewOrderDto> viewStoreOrders(
            @PathVariable Long storeId,
            HttpServletRequest request
    ){
        return ownerStoreService.viewStoreOrders(storeId, request);
    }

}
