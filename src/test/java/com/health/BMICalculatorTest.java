package com.health;

import com.health.BMICalculator;
import com.health.Coder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BMICalculatorTest {


    @BeforeAll
    static void beforeAll() {
        // Most use case is to open a database connection or a connecting to a service
        System.out.println("Run BEFORE all unit tests");
    }

    @AfterAll
    static void afterAll() {
        // Most use case is to close connection to a database or a service
        System.out.println("Run AFTER all unit tests");
    }

    @Nested
    @DisplayName("Check if Diet Recommended")
    class isDietRecommendedTests {
        @Test
        void should_Return_False_When_DietNotRecommended() {
            // Given
            double weight = 90;
            double height = 1.9;

            // When
            boolean isRecommended = BMICalculator.isDietRecommended(weight, height);

            // Then
            assertFalse(isRecommended);
        }

        @ParameterizedTest(name = "Weight={0} ---- Height={1}")
//    @CsvSource(value = {"1.90, 96", "1.80, 85", "1.70, 78", "1.65, 70"})
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void should_Return_True_When_DietRecommended(double coderWeight, double coderHeight) {
            // Given
            double weight = coderWeight;
            double height = coderHeight;

            // When
            boolean isRecommended = BMICalculator.isDietRecommended(weight, height);

            // Then
            assertTrue(isRecommended);
        }
    }

    @Nested
    @DisplayName("Find Coder with Worst BMI")
    class coderWithWorstBMITests {
        private String envirement = "dev";

        @Test
        void should_returnCoderWithWorstBMI_When_CoderListNotEmpty() {
            // Given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.9, 90));
            coders.add(new Coder(1.8, 85));
            coders.add(new Coder(1.7, 100));
            // When
            Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
            // Then
            assertAll(
                    () -> assertEquals(1.7, coderWithWorstBMI.getHeight()),
                    () -> assertEquals(100, coderWithWorstBMI.getWeight()));

        }

        @Test
        void should_returnCoderWithWorstBMIIn1MS_When_CoderListHas10000Elements() {
            // Given
            assumeTrue(this.envirement.equals("dev"));
            List<Coder> coders = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                coders.add(new Coder(1.0 + i, 10.0 + i));
            }
            // when
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
            // Then
            assertTimeout(Duration.ofMillis(6), executable);

        }

        @Test
        void should_returnNullWorstBMI_When_CoderListEmpty() {
            // Given
            List<Coder> coders = new ArrayList<>();

            // When
            Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
            // Then
            assertNull(coderWithWorstBMI);

        }
    }


    @DisplayName("Throw Exception When Height Equal Zero")
    @RepeatedTest(5)
    void should_Throw_ArithmeticException_When_HeightZero() {
        // Given
        double weight = 100;
        double height = 0.0;

        // When
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

        // Then
        assertThrows(ArithmeticException.class, executable);
    }

    @Nested
    @DisplayName("Display BMI Score List")
    class getBMIScores {
        @Test
        void should_returnBMIScoreList_When_CoderListNotEmpty() {
            // Given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.9, 90));
            coders.add(new Coder(1.8, 85));
            coders.add(new Coder(1.7, 100));

            double[] expectedScores = {
                    BMICalculator.getBMIScore(1.9, 90),
                    BMICalculator.getBMIScore(1.8, 85),
                    BMICalculator.getBMIScore(1.7, 100)
            };

            // When
            double[] coderScoreList = BMICalculator.getBMIScores(coders);
            // Then
            assertArrayEquals(expectedScores, coderScoreList);

        }
    }


}