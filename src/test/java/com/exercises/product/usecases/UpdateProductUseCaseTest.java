package com.exercises.product.usecases;

import com.exercises.product.domain.Product;
import com.exercises.product.domain.ProductFactory;
import com.exercises.product.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateProductUseCaseTest {

    private ProductFactory factory;

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private UpdateProductUseCase sut;

    @BeforeEach
    void setup() {
        factory = new ProductFactory(repository);
    }

    @Nested
    @DisplayName("When the input is null or blank")
    class NullAndBlankEntries {

        @Test
        @DisplayName("Should throw IllegalArgumentException when the input product object is null")
        void shouldThrowExceptionWhenInputIsNull() {
            assertThrows(IllegalArgumentException.class, () -> sut.update(null));
        }
    }

    @Nested
    @DisplayName("When the input fails pre-conditions")
    class InvalidEntries {

        @Test
        @DisplayName("Should throw NoSuchElementException when the product was not found")
        void shouldThrowExceptionWhenProductIsNotFound() {
            Product product = factory.createProductFaker();
            System.out.println(product.toString());

            when(repository.findById(product.getId())).thenReturn(Optional.empty());

            assertThrows(NoSuchElementException.class, () -> sut.update(product));
            verify(repository).findById(product.getId());
        }
    }
}
