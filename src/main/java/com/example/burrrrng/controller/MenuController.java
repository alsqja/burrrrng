package com.example.burrrrng.controller;


import com.example.burrrrng.dto.RequestMenuDto;
import com.example.burrrrng.dto.ResponseMenuDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner/stores")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{id}/menus")
    public CommonResDto<ResponseMenuDto> createMenu(
            @PathVariable Long id,
            @RequestBody RequestMenuDto requestMenuDto) {
        return menuService.createMenu(id, requestMenuDto);
    }



}
