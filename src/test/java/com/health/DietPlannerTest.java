package com.health;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DietPlannerTest {

    private DietPlanner dietPlanner;

    @BeforeEach
    void setup() {
        this.dietPlanner = new DietPlanner(20, 30, 50);
    }

    @AfterEach
    void afterEach() {
        System.out.println("Unit test Finished!");
    }

    @Test
    void should_ReturnCorrectDietPlan_When_CorrectCoder() {
        // Given
        Coder coder = new Coder(1.9, 100, 33, Gender.MALE);
        DietPlan expectedDiet = new DietPlan(2607, 130, 87, 326);
        // When
        DietPlan actualDietPlan = dietPlanner.calculateDiet(coder);
        // Then
        assertAll(
                () -> assertEquals(expectedDiet.getCalories(), actualDietPlan.getCalories()),
                () -> assertEquals(expectedDiet.getCarbohydrate(), actualDietPlan.getCarbohydrate()),
                () -> assertEquals(expectedDiet.getProtein(), actualDietPlan.getProtein()),
                () -> assertEquals(expectedDiet.getFat(), actualDietPlan.getFat())
        );
    }

}