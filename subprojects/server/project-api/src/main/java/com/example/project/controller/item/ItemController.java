package com.example.project.controller.item;

import com.example.project.controller.item.dto.ItemRequestDto;
import com.example.project.controller.item.dto.ItemResponseDto;
import com.example.project.service.item.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemResponseDto.Get> getItems(Pageable pageable) {
        return itemService.getItems(pageable);
    }

    @GetMapping("/{itemId}")
    public ItemResponseDto.Get getCategoryById(@PathVariable("itemId") Long itemId) {
        return itemService.getItemById(itemId);
    }

    @PostMapping
    public ItemResponseDto.Get createItem(@RequestBody ItemRequestDto.Post dto) {
        return itemService.createItem(dto);
    }

    @PutMapping("/{itemId}")
    public ItemResponseDto.Get updateItem(
        @PathVariable("itemId") Long itemId,
        @RequestBody ItemRequestDto.Put dto) {
        return itemService.updateItem(itemId, dto);
    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto.Get updateItem(
        @PathVariable("itemId") Long itemId,
        @RequestBody ItemRequestDto.Patch dto) {
        return itemService.updateItem(itemId, dto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId) {
        itemService.deleteItem(itemId);
    }
}
