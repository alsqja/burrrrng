package com.example.burrrrng.service;

import com.example.burrrrng.dto.MenuResDto;
import com.example.burrrrng.dto.StoreAllResDto;
import com.example.burrrrng.dto.StoreResDto;

import java.util.List;

public interface StoreService {
    
    List<StoreResDto> findAllStores();

    StoreAllResDto findById(Long id);

    List<MenuResDto> findAllStoreMenus(Long id);
}
