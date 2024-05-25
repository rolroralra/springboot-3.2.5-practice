package com.example.project.repository.jpa.entity.item;

import com.example.project.repository.jpa.entity.BaseTimeEntity;
import com.example.project.repository.jpa.entity.brand.BrandEntity;
import com.example.project.repository.jpa.entity.category.CategoryEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ITEM")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    String name;

    Long price;

    public ItemEntity(String name, Long price) {
        this.name = name;
        this.price = price;
    }
}
