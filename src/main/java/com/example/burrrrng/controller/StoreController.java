package com.example.burrrrng.controller;

import com.example.burrrrng.dto.StoreAllResDto;
import com.example.burrrrng.dto.StoreListResDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<CommonResDto<StoreListResDto>> findAllStores() {

        return new ResponseEntity<>(
                new CommonResDto<>("가게 조회 완료", new StoreListResDto(storeService.findAllStores())),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResDto<StoreAllResDto>> findById(@PathVariable Long id) {

        return new ResponseEntity<>(new CommonResDto<>("가게 조회 완료", storeService.findById(id)), HttpStatus.OK);
    }
}
