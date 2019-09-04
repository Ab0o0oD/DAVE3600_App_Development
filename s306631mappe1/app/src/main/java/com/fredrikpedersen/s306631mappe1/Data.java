package com.fredrikpedersen.s306631mappe1;

/**
 * Class for storing shared data between classes
 */

public class Data {

    private static int numberOfTasks = 5;

    public static int getNumberOfTasks() {
        return numberOfTasks;
    }

    public static void setNumberOfTasks(int numberOfTasks) {
        Data.numberOfTasks = numberOfTasks;
    }
}
