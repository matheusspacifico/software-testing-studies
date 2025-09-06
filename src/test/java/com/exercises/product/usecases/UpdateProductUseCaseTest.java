package com.exercises.product.usecases;

import com.exercises.product.domain.Product;
import com.exercises.product.domain.ProductFactory;
import com.exercises.product.repository.ProductRepository;
import com.exercises.product.repository.ProductRepositoryDummyImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
            when(repository.findById(product.getId())).thenReturn(Optional.empty());
            assertThrows(NoSuchElementException.class, () -> sut.update(product));
        }
    }

    @Nested
    @DisplayName("When the input is valid (keep in mind null field entries are valid and they are just skipped)")
    class ValidEntries {

        @Test
        @DisplayName("Should update every field when all fields are valid and non null and return an empty invalid entries list")
        void shouldUpdateEveryFieldWhenAllFieldsAreValidAndNonNullAndReturnAnEmptyInvalidEntriesList() {
            Product existingProduct = factory.createProductFaker();
            Product newProduct = factory.createProductFakerSetId(existingProduct.getId());

            when(repository.findById(newProduct.getId())).thenReturn(Optional.of(existingProduct));

            Map<String, String> invalidEntries = sut.update(newProduct);

            ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
            verify(repository).saveOrUpdate(productCaptor.capture());

            Product savedProduct = productCaptor.getValue();

            assertTrue(invalidEntries.isEmpty());
            assertAll(
                    () -> assertEquals(savedProduct.getId(), existingProduct.getId()),
                    () -> assertEquals(savedProduct.getName(), newProduct.getName()),
                    () -> assertEquals(savedProduct.getDescription(), newProduct.getDescription()),
                    () -> assertEquals(savedProduct.getPrice(), newProduct.getPrice()),
                    () -> assertEquals(savedProduct.getQuantity(), newProduct.getQuantity()),
                    () -> assertEquals(savedProduct.getCategory(), newProduct.getCategory())
            );
        }

        @ParameterizedTest
        @MethodSource("mixedNullAndNonNullFields")
        @DisplayName("Should update only valid fields and return the proper invalid entry map for a mix of different entries")
        void shouldUpdateOnlyValidFieldsAndReturnTheProperInvalidEntryMapForAMixOfDifferentEntries(Product inputProduct, Map<String, String> expectedInvalidEntries) {
            Product existingProduct = factory.createProductFakerSetId(inputProduct.getId());

            when(repository.findById(inputProduct.getId())).thenReturn(Optional.of(existingProduct));

            Map<String, String> invalidEntries = sut.update(inputProduct);

            assertEquals(expectedInvalidEntries, invalidEntries);
        }


        private static Stream<Arguments> mixedNullAndNonNullFields() {
            ProductRepository r = new ProductRepositoryDummyImpl();
            ProductFactory f = new ProductFactory(r);

            Product productWithNullName = f.createProductFakerSetName(null);
            Product productWithBlankName = f.createProductFakerSetName("");
            Product productWithNullPrice = f.createProductFakerSetPrice(null);
            Product productWithZeroPrice = f.createProductFakerSetPrice(0.0);
            Product productWithNullCategory = f.createProductFakerSetCategory(null);
            Product productWithNullQuantity = f.createProductFakerSetQuantity(null);
            Product productWithZeroQuantity = f.createProductFakerSetQuantity(0);
            Product productWithNullDescription = f.createProductFakerSetDescription(null);
            Product productWithBlankDescription = f.createProductFakerSetDescription("");

            return Stream.of(
                    Arguments.of(productWithNullName, Collections.emptyMap()),
                    Arguments.of(productWithBlankName, Map.of("", "Name cannot be blank!")),
                    Arguments.of(productWithNullPrice, Collections.emptyMap()),
                    Arguments.of(productWithZeroPrice, Map.of("0.0", "Price cannot be zero or negative!")),
                    Arguments.of(productWithNullCategory, Collections.emptyMap()),
                    Arguments.of(productWithNullQuantity, Collections.emptyMap()),
                    Arguments.of(productWithZeroQuantity, Map.of("0", "Quantity cannot be zero or negative!")),
                    Arguments.of(productWithNullDescription, Collections.emptyMap()),
                    Arguments.of(productWithBlankDescription, Map.of("", "Description cannot be blank!"))
            );
        }
    }
}
