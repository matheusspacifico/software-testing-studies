package com.exercises.product.usecases;

import com.exercises.product.domain.Product;
import com.exercises.product.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private UpdateProductUseCase sut;

    @Nested
    @DisplayName("When the input is null or blank")
    class NullAndBlankEntries {

        @Test
        @DisplayName("Should throw IllegalArgumentException when the input product object is null")
        void shouldThrowExceptionWhenInputIsNull() {
            assertThrows(IllegalArgumentException.class, () -> sut.update(null));
        }
    }
}
