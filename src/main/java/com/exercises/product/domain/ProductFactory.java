package com.exercises.product.domain;

import com.exercises.product.repository.ProductRepository;

public class ProductFactory {
    private final ProductRepository repository;

    public ProductFactory(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public Product createProduct(String name, String description, Double price, Integer quantity, Category category) {
        Long lastId = repository.findLastId();

        if (lastId == null) lastId = 1L;
        else lastId = lastId + 1;

        return new Product(lastId, name, description, price, quantity, category);
    }
}
