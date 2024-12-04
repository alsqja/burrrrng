package com.example.burrrrng.service;

import com.example.burrrrng.dto.StoreResDto;

import java.util.List;

public interface StoreService {
    List<StoreResDto> findAllStores();
}
