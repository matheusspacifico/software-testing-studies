package com.exercises.triangle;

import org.junit.jupiter.api.*;
import com.exercises.triangle.TriangleUtils.Statistics;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Tag("UnitTest")
public class TriangleUtilsTest {

    private TriangleUtils sut;

    @BeforeEach
    void setup() {
        sut = new TriangleUtils();
    }

    @Nested
    class InvalidEntries {

        @Test
        @DisplayName("T1: Should throw NullPointerException when data is null")
        void ShouldThrowNullPointerExceptionWhenDataIsNull() {
            Assertions.assertThrows(NullPointerException.class, () -> sut.computeTypeStatistics(null));
        }

        @Test
        @DisplayName("T2: Should throw IllegalArgumentException when data has a subarray with five elements")
        void ShouldThrowIllegalArgumentExceptionWhenDataHasSubarrayWithFiveElements() {
            double[][] data = {{1, 2, 3, 4, 5}};
            Assertions.assertThrows(IllegalArgumentException.class, () -> sut.computeTypeStatistics(data));
        }

        @Test
        @DisplayName("T3: Should throw IllegalArgumentException when data has a subarray with seven elements")
        void ShouldThrowIllegalArgumentExceptionWhenDataHasSubarrayWithSevenElements() {
            double[][] data = {{1, 2, 3, 4, 5, 6, 7}};
            Assertions.assertThrows(IllegalArgumentException.class, () -> sut.computeTypeStatistics(data));
        }
    }

    @Nested
    class ValidEntries {

        @Test
        @DisplayName("T4: Should return empty Statistics when data is empty")
        void ShouldReturnEmptyStatisticsWhenDataIsEmpty() {
            double[][] data = {};
            var result = new Statistics(0, 0, 0, 0);

            Assertions.assertEquals(result, sut.computeTypeStatistics(data));
        }

        @ParameterizedTest
        @MethodSource("mixOfDataEntriesAndResults")
        @DisplayName("T5, T6, T7, T8: Should return the correct Statistics object with only one data subarray")
        void ShouldReturnTheCorrectStatisticsWithOnlyOneDataSubarray(double[][] entry, Statistics result) {
            Assertions.assertEquals(result, sut.computeTypeStatistics(entry));
        }

        @Test
        @DisplayName("T9: Should return the correct Statistics object with mixed triangle subarrays")
        void ShouldReturnTheCorrectStatisticsWithMixedSubarrays() {
            double[][] data = {{0, 0, 0, 0, 5, 5}, {0, 0, 2, 0, 1, 1.732}, {-2, 0, 2, 0, 0, 3}, {0, 0, 3, 0, 1, 2}};
            var result = new Statistics(1, 1, 1, 1);

            Assertions.assertEquals(result, sut.computeTypeStatistics(data));
        }

        private static Stream<Arguments> mixOfDataEntriesAndResults() {
            double[][] nonTriangleEntry = {{0, 0, 0, 0, 5, 5}};
            double[][] equilateralEntry = {{0, 0, 2, 0, 1, 1.732}};
            double[][] isoscelesEntry = {{-2, 0, 2, 0, 0, 3}};
            double[][] scaleneEntry = {{0, 0, 3, 0, 1, 2}};

            var nonTriangleResult = new Statistics(1, 0, 0, 0);
            var equilateralResult = new Statistics(0, 1, 0, 0);
            var isoscelesResult = new Statistics(0, 0, 1, 0);
            var scaleneResult = new Statistics(0, 0, 0, 1);

            return Stream.of(
                    Arguments.of(nonTriangleEntry, nonTriangleResult),
                    Arguments.of(equilateralEntry, equilateralResult),
                    Arguments.of(isoscelesEntry, isoscelesResult),
                    Arguments.of(scaleneEntry, scaleneResult)
            );
        }
    }
}
