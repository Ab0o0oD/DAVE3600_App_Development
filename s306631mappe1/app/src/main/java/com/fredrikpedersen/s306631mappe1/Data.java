package com.fredrikpedersen.s306631mappe1;

class Data {

    private static int correct = 0;
    private static int wrong = 0;
    private static int numberOfTasks = 5;

    public static int getCorrect() {
        return correct;
    }

    public static int getWrong() {
        return wrong;
    }

    public static int getNumberOfTasks() {
        return numberOfTasks;
    }

    public static void setCorrect(int correct) {
        Data.correct = correct;
    }

    public static void setWrong(int wrong) {
        Data.wrong = wrong;
    }

    public static void setNumberOfTasks(int numberOfTasks) {
        Data.numberOfTasks = numberOfTasks;
    }


    public static void incrementCorrect(int increment) {
        correct += increment;
    }

    public static void incrementWrong(int increment) {
        wrong += increment;
    }
}
