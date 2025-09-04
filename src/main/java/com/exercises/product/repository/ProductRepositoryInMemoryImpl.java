package com.exercises.product.repository;

import com.exercises.product.domain.Product;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ProductRepositoryInMemoryImpl implements ProductRepository {
    private final Map<Long, Product> products;

    public ProductRepositoryInMemoryImpl() {
        this.products = new LinkedHashMap<>();
    }

    @Override
    public Optional<Product> findById(Long id) {
        if (products.containsKey(id)) return Optional.of(products.get(id));

        return Optional.empty();
    }

    @Override
    public Long findLastId() {
        if (products.isEmpty()) return 0L;

        return products.values().iterator().next().getId();
    }

    @Override
    public void saveOrUpdate(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public void delete(Product product) {
        products.remove(product.getId());
    }
}
