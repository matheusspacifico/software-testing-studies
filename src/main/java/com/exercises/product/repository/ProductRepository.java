package com.exercises.product.repository;

import com.exercises.product.domain.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);
    void save(Product product);
    void delete(Product product);
    void update(Product product);
}
