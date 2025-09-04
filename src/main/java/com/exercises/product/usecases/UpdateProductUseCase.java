package com.exercises.product.usecases;

import com.exercises.product.domain.Category;
import com.exercises.product.domain.Product;
import com.exercises.product.repository.ProductRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UpdateProductUseCase {
    private final ProductRepository repository;
    private final Map<String, String> invalidEntries;

    public UpdateProductUseCase(ProductRepository repository) {
        this.repository = repository;
        this.invalidEntries = new ConcurrentHashMap<>();
    }

    public Map<String, String> update(Product newProduct) {
        if (Objects.isNull(newProduct)) {
            throw new IllegalArgumentException("Product must not be null!");
        }

        Optional<Product> optionalProduct = repository.findById(newProduct.getId());
        if (optionalProduct.isEmpty()) {
            throw new NoSuchElementException("Product with id: " + newProduct.getId() + " not found!");
        }

        invalidEntries.clear();

        Product existingProduct = optionalProduct.get();

        changeName(newProduct.getName(), existingProduct);
        changePrice(newProduct.getPrice(), existingProduct);
        changeCategory(newProduct.getCategory(), existingProduct);
        changeQuantity(newProduct.getQuantity(), existingProduct);
        changeDescription(newProduct.getDescription(), existingProduct);

        if (!invalidEntries.isEmpty()) {
            return invalidEntries;
        }

        repository.saveOrUpdate(existingProduct);
        return invalidEntries;
    }

    private void changeName(String newName, Product existingProduct) {
        if (newName == null) return;
        if (newName.isBlank()) {
            invalidEntries.put(newName, "Name cannot be blank!");
            return;
        }
        if (!existingProduct.getName().equals(newName)) {
            existingProduct.setName(newName);
        }
    }

    private void changePrice(Double newPrice, Product existingProduct) {
        if (newPrice == null) return;
        if (newPrice <= 0) {
            invalidEntries.put(newPrice.toString(), "Price cannot be zero or negative!");
            return;
        }
        if (!existingProduct.getPrice().equals(newPrice)) {
            existingProduct.setPrice(newPrice);
        }
    }

    private void changeCategory(Category newCategory, Product existingProduct) {
        if (newCategory == null) return;
        if (!existingProduct.getCategory().equals(newCategory)) {
            existingProduct.setCategory(newCategory);
        }
    }

    private void changeQuantity(Integer newQuantity, Product existingProduct) {
        if (newQuantity == null) return;
        if (newQuantity <= 0) {
            invalidEntries.put(newQuantity.toString(), "Quantity cannot be zero or negative!");
            return;
        }
        if (!existingProduct.getQuantity().equals(newQuantity)) {
            existingProduct.setQuantity(newQuantity);
        }
    }

    private void changeDescription(String newDescription, Product existingProduct) {
        if (newDescription == null) return;
        if (newDescription.isBlank()) {
            invalidEntries.put(newDescription, "Description cannot be blank!");
            return;
        }
        if (!existingProduct.getDescription().equals(newDescription)) {
            existingProduct.setDescription(newDescription);
        }
    }
}