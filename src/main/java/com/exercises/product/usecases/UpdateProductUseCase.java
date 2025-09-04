package com.exercises.product.usecases;

import com.exercises.product.domain.Category;
import com.exercises.product.domain.Product;
import com.exercises.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UpdateProductUseCase {
    private final ProductRepository repository;
    private final List<String> invalidEntries;

    public UpdateProductUseCase(ProductRepository repository) {
        this.repository = repository;
        this.invalidEntries = new ArrayList<>();
    }

    public List<String> update(Product newProduct) {
        invalidEntries.clear();

        Optional<Product> optionalProduct = repository.findById(newProduct.getId());
        if (optionalProduct.isEmpty()) {
            throw new NoSuchElementException("Product with id: " + newProduct.getId() + " not found!");
        }

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
            invalidEntries.add("Product name cannot be blank.");
            return;
        }
        if (!existingProduct.getName().equals(newName)) {
            existingProduct.setName(newName);
        }
    }

    private void changePrice(Double newPrice, Product existingProduct) {
        if (newPrice == null) return;
        if (newPrice <= 0) {
            invalidEntries.add("Price must be a positive number.");
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
            invalidEntries.add("Quantity must be a positive number.");
            return;
        }
        if (!existingProduct.getQuantity().equals(newQuantity)) {
            existingProduct.setQuantity(newQuantity);
        }
    }

    private void changeDescription(String newDescription, Product existingProduct) {
        if (newDescription == null) return;
        if (newDescription.isBlank()) {
            invalidEntries.add("Description cannot be blank.");
            return;
        }
        if (!existingProduct.getDescription().equals(newDescription)) {
            existingProduct.setDescription(newDescription);
        }
    }
}