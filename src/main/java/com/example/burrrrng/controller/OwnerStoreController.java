package com.example.burrrrng.controller;


import com.example.burrrrng.dto.RequestOwnerStoreDto;
import com.example.burrrrng.dto.ResponseMenuDto;
import com.example.burrrrng.dto.ResponseOwnerStoreDto;
import com.example.burrrrng.dto.common.CommonListResDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.service.OwnerStoreService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owner/stores")
@RequiredArgsConstructor
public class OwnerStoreController {

    private final OwnerStoreService ownerStoreService;

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

}
