package com.example.burrrrng.controller;


import com.example.burrrrng.dto.RequestMenuCreateDto;
import com.example.burrrrng.dto.RequestMenuUpdateDto;
import com.example.burrrrng.dto.ResponseMenuDto;
import com.example.burrrrng.dto.common.CommonResDto;
import com.example.burrrrng.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
            @Valid @RequestBody RequestMenuCreateDto requestMenuCreateDto, HttpServletRequest request) {
        return menuService.createMenu(id, requestMenuCreateDto, request);
    }

    @PatchMapping("/{storeId}/menus/{menuId}")
    public CommonResDto<ResponseMenuDto> updateMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody RequestMenuUpdateDto requestMenuUpdateDto, HttpServletRequest request){
        return menuService.updateMenu(storeId, menuId, requestMenuUpdateDto, request);

    }



}
