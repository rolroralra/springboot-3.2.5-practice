package com.example.project.controller.item;

import com.example.project.controller.item.dto.ItemResponseDto;
import com.example.project.repository.jpa.repository.item.ItemJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemJpaRepository itemJpaRepository;

    @GetMapping
    public List<ItemResponseDto.Get> getItems(Pageable pageable) {
        return itemJpaRepository.findAll(pageable).stream()
            .map(ItemResponseDto.Get::fromEntity)
            .toList();
    }

    @GetMapping("/{itemId}")
    public ItemResponseDto.Get getCategoryById(@PathVariable("itemId") Long itemId) {
        return ItemResponseDto.Get.fromEntity(
            itemJpaRepository.findById(itemId).orElseThrow()
        );
    }
}
