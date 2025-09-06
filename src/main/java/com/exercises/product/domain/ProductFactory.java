package com.exercises.product.domain;

import com.exercises.product.repository.ProductRepository;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Random;

public class ProductFactory {
    private final ProductRepository repository;

    public ProductFactory(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    public Product createProductManually(String name, String description, Double price, Integer quantity, Category category) {
        Long lastId = repository.findLastId();

        return new Product(lastId + 1, name, description, price, quantity, category);
    }

    public Product createProductManuallySetId(Long id, String name, String description, Double price, Integer quantity, Category category) {
        return new Product(id, name, description, price, quantity, category);
    }

    public Product createProductFaker() {
        Long lastId = repository.findLastId();
        Faker f = new Faker();
        Random r = new Random();

        List<Category> c = List.of(Category.values());
        int randomIndex = r.nextInt(c.size());

        return new Product(lastId + 1, f.leagueOfLegends().champion(), f.leagueOfLegends().quote(), f.number().randomDouble(2, 1, 10000), f.number().numberBetween(1, 100), c.get(randomIndex));
    }

    public Product createProductFakerSetId(Long id) {
        Faker f = new Faker();
        Random r = new Random();

        List<Category> c = List.of(Category.values());
        int randomIndex = r.nextInt(c.size());

        return new Product(id, f.leagueOfLegends().champion(), f.leagueOfLegends().quote(), f.number().randomDouble(2, 1, 10000), f.number().numberBetween(1, 100), c.get(randomIndex));
    }

    public Product createProductFakerSetName(String name) {
        Long lastId = repository.findLastId();
        Faker f = new Faker();
        Random r = new Random();

        List<Category> c = List.of(Category.values());
        int randomIndex = r.nextInt(c.size());

        return new Product(lastId + 1, name, f.leagueOfLegends().quote(), f.number().randomDouble(2, 1, 10000), f.number().numberBetween(1, 100), c.get(randomIndex));
    }

    public Product createProductFakerSetDescription(String description) {
        Long lastId = repository.findLastId();
        Faker f = new Faker();
        Random r = new Random();

        List<Category> c = List.of(Category.values());
        int randomIndex = r.nextInt(c.size());

        return new Product(lastId + 1, f.leagueOfLegends().champion(), description, f.number().randomDouble(2, 1, 10000), f.number().numberBetween(1, 100), c.get(randomIndex));
    }

    public Product createProductFakerSetPrice(Double price) {
        Long lastId = repository.findLastId();
        Faker f = new Faker();
        Random r = new Random();

        List<Category> c = List.of(Category.values());
        int randomIndex = r.nextInt(c.size());

        return new Product(lastId + 1, f.leagueOfLegends().champion(), f.leagueOfLegends().quote(), price, f.number().numberBetween(1, 100), c.get(randomIndex));
    }

    public Product createProductFakerSetQuantity(Integer quantity) {
        Long lastId = repository.findLastId();
        Faker f = new Faker();
        Random r = new Random();

        List<Category> c = List.of(Category.values());
        int randomIndex = r.nextInt(c.size());

        return new Product(lastId + 1, f.leagueOfLegends().champion(), f.leagueOfLegends().quote(), f.number().randomDouble(2, 1, 10000), quantity, c.get(randomIndex));
    }

    public Product createProductFakerSetCategory(Category category) {
        Long lastId = repository.findLastId();
        Faker f = new Faker();

        return new Product(lastId + 1, f.leagueOfLegends().champion(), f.leagueOfLegends().quote(), f.number().randomDouble(2, 1, 10000), f.number().numberBetween(1, 100), category);
    }
}
