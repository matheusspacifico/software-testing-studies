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

@Tag("UnitTest")
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
        @DisplayName("T1: Should throw IllegalArgumentException when the input product object is null")
        void shouldThrowExceptionWhenInputIsNull() {
            assertThrows(IllegalArgumentException.class, () -> sut.update(null));
        }
    }

    @Nested
    @DisplayName("When the input fails pre-conditions")
    class InvalidEntries {

        @Test
        @DisplayName("T2: Should throw NoSuchElementException when the product was not found")
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
        @DisplayName("T3: Should update every field when all fields are valid and non null and return an empty invalid entries list")
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
        @DisplayName("T4-T12: Should update only valid fields and return the proper invalid entry map for a mix of different entries")
        void shouldUpdateOnlyValidFieldsAndReturnTheProperInvalidEntryMapForAMixOfDifferentEntries(Product inputProduct, Map<String, String> expectedInvalidEntries) {
            Product existingProduct = factory.createProductFakerSetId(inputProduct.getId());

            when(repository.findById(inputProduct.getId())).thenReturn(Optional.of(existingProduct));

            Map<String, String> invalidEntries = sut.update(inputProduct);

            assertEquals(expectedInvalidEntries, invalidEntries);
        }


        @Test
        @DisplayName("T13: Should not update any field with all null fields input and return an empty map")
        void shouldNotUpdateAnyFieldWithAllNullFieldsInputAndReturnAnEmptyMap() {
            Product existingProduct = factory.createProductFaker();
            Product newProduct = factory.createProductManuallySetId(existingProduct.getId(), null, null, null, null, null);

            when(repository.findById(newProduct.getId())).thenReturn(Optional.of(existingProduct));

            Map<String, String> invalidEntries = sut.update(newProduct);

            verify(repository, never()).saveOrUpdate(any(Product.class));
            assertTrue(invalidEntries.isEmpty());
        }

        @Test
        @DisplayName("T14: Should not update any field with all fields equals existing product fields and return an empty map")
        void shouldNotUpdateAnyFieldWithAllFieldsEqualsExistingProductFieldsAndReturnAnEmptyMap() {
            Product existingProduct = factory.createProductFaker();
            Product newProduct = factory.createProductManuallySetId(
                    existingProduct.getId(),
                    existingProduct.getName(),
                    existingProduct.getDescription(),
                    existingProduct.getPrice(),
                    existingProduct.getQuantity(),
                    existingProduct.getCategory()
            );

            when(repository.findById(newProduct.getId())).thenReturn(Optional.of(existingProduct));

            Map<String, String> invalidEntries = sut.update(newProduct);

            verify(repository, never()).saveOrUpdate(any(Product.class));
            assertTrue(invalidEntries.isEmpty());
        }

        @Test
        @DisplayName("T15: Should return a full map of errors when all possible fields are invalid")
        void shouldReturnFullErrorMapWhenAllFieldsAreInvalid() {
            Product existingProduct = factory.createProductFakerSetId(1L);
            Product newProduct = factory.createProductManuallySetId(existingProduct.getId(), "", "  ", 0.0, -5, null);

            when(repository.findById(newProduct.getId())).thenReturn(Optional.of(existingProduct));

            Map<String, String> invalidEntries = sut.update(newProduct);

            assertEquals(4, invalidEntries.size());
            assertTrue(invalidEntries.containsValue("Name cannot be blank!"));
            assertTrue(invalidEntries.containsValue("Description cannot be blank!"));
            assertTrue(invalidEntries.containsValue("Price cannot be zero or negative!"));
            assertTrue(invalidEntries.containsValue("Quantity cannot be zero or negative!"));

            verify(repository, never()).saveOrUpdate(any(Product.class));
        }

        @Test
        @DisplayName("T16, T17: Should return a partial error map for a mix of valid and invalid fields")
        void shouldReturnPartialErrorMapForMixedValidAndInvalidFields() {
            Product existingProduct = factory.createProductFakerSetId(1L);
            Product newProduct = factory.createProductManuallySetId(existingProduct.getId(), "Valid New Name", "", -99.9, 100, null);

            when(repository.findById(newProduct.getId())).thenReturn(Optional.of(existingProduct));

            Map<String, String> invalidEntries = sut.update(newProduct);

            assertEquals(2, invalidEntries.size());
            assertTrue(invalidEntries.containsValue("Description cannot be blank!"));
            assertTrue(invalidEntries.containsValue("Price cannot be zero or negative!"));

            verify(repository, never()).saveOrUpdate(any(Product.class));
        }

        @Test
        @DisplayName("T18, T19, T20: Should update only one field when all others are null")
        void shouldUpdateOnlyOneFieldWhenAllOthersAreNull() {
            Product existingProduct = factory.createProductFakerSetId(1L);
            Product newProduct = factory.createProductManuallySetId(existingProduct.getId(), null, null, 999.99, null, null);

            when(repository.findById(newProduct.getId())).thenReturn(Optional.of(existingProduct));

            Map<String, String> invalidEntries = sut.update(newProduct);

            assertTrue(invalidEntries.isEmpty());

            ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
            verify(repository).saveOrUpdate(productCaptor.capture());

            Product savedProduct = productCaptor.getValue();

            assertEquals(999.99, savedProduct.getPrice());
            assertEquals(existingProduct.getName(), savedProduct.getName());
            assertEquals(existingProduct.getQuantity(), savedProduct.getQuantity());
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
