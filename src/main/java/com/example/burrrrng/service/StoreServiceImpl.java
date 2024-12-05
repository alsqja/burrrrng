package com.example.burrrrng.service;

import com.example.burrrrng.dto.MenuResDto;
import com.example.burrrrng.dto.StoreAllResDto;
import com.example.burrrrng.dto.StoreResDto;
import com.example.burrrrng.entity.Store;
import com.example.burrrrng.repository.MenuRepository;
import com.example.burrrrng.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Override
    public List<StoreResDto> findAllStores() {

        System.out.println(storeRepository.findAllStoresWithStar());
        return storeRepository.findAllStoresWithStar();
    }

    @Override
    public StoreAllResDto findById(Long id) {
        return new StoreAllResDto(storeRepository.findByIdOrElseThrow(id));
    }

    @Override
    public List<MenuResDto> findAllStoreMenus(Long id) {
        Store store = storeRepository.findByIdOrElseThrow(id);

        return menuRepository.findByStore(store).stream().map(MenuResDto::new).toList();
    }
}
