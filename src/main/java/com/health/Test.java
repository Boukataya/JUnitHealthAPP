package com.health;

public class Test {

    public static void main(String[] args) {

        Coder coder = new Coder(1.9, 100, 33, Gender.MALE);
        DietPlanner dietPlanner=new DietPlanner(20,30,50);


        System.out.println(dietPlanner.calculateDiet(coder));
    }

}
