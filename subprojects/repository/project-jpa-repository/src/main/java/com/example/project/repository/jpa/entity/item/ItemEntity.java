package com.example.project.repository.jpa.entity.item;

import com.example.project.repository.jpa.entity.BaseTimeEntity;
import com.example.project.repository.jpa.entity.brand.BrandEntity;
import com.example.project.repository.jpa.entity.category.CategoryEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ITEM")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ItemEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    String name;

    Long price;

    public ItemEntity(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
            "id=" + id +
            ", brand=" + brand +
            ", category=" + category +
            ", name='" + name + '\'' +
            ", price=" + price +
            '}';
    }

    public String getBrandName() {
        return Optional.ofNullable(brand).map(BrandEntity::getName).orElse("N/A");
    }

    public String getCategoryName() {
        return category.getName();
    }

    public Long getBrandId() {
        return brand.getId();
    }

    public void changeBrand(BrandEntity brand) {
        if (brand != null) {
            setBrand(brand);
        }
    }

    public void changeCategory(CategoryEntity category) {
        if (category != null) {
            setCategory(category);
        }
    }

    public void changeName(String name) {
        if (name != null) {
            setName(name);
        }
    }

    public void changePrice(Long price) {
        if (price != null && price >= 0) {
            setPrice(price);
        }
    }
}
