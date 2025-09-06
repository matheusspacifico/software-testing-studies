package com.exercises.product.repository;

import com.exercises.product.domain.Product;

import java.util.Optional;

public class ProductRepositoryDummyImpl implements ProductRepository {

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Long findLastId() {
        return 20L;
    }

    @Override
    public void saveOrUpdate(Product product) {

    }

    @Override
    public void delete(Product product) {

    }
}
