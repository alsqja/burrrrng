package com.example.burrrrng.service;

import com.example.burrrrng.dto.StoreResDto;
import com.example.burrrrng.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public List<StoreResDto> findAllStores() {

        System.out.println(storeRepository.findAllStoresWithStar());
        return storeRepository.findAllStoresWithStar();
    }
}
