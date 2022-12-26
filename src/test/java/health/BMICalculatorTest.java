package health;

import com.health.BMICalculator;
import com.health.Coder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    @Test
    @DisplayName("Diet not recommended")
    void should_Return_False_When_DietNotRecommended() {
        // Given
        double weight = 90;
        double height = 1.9;

        // When
        boolean isRecommended = BMICalculator.isDietRecommended(weight, height);

        // Then
        assertFalse(isRecommended);
    }

    @Test
    @DisplayName("Diet is recommended")
    void should_Return_True_When_DietRecommended() {
        // Given
        double weight = 100;
        double height = 1.9;

        // When
        boolean isRecommended = BMICalculator.isDietRecommended(weight, height);

        // Then
        assertTrue(isRecommended);
    }

    @Test
    @DisplayName("Height exception when equal zero")
    void should_Throw_ArithmeticException_When_HeightZero() {
        // Given
        double weight = 100;
        double height = 0.0;

        // When
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

        // Then
        assertThrows(ArithmeticException.class, executable);
    }

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
    void should_returnNullWorstBMI_When_CoderListEmpty() {
        // Given
        List<Coder> coders = new ArrayList<>();

        // When
        Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
        // Then
        assertNull(coderWithWorstBMI);

    }

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