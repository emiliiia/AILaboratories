package org.ai;
/*
  @author emilia
  @project AI
  @class Chromosome
  @version 1.0.0
  @since 10.11.2023 - 21:55
*/

import java.util.Arrays;
import java.util.Random;

public class TSPGeneticAlgorithm {

    private static final int NUM_CITIES = 5; // Кількість міст
    private static final int POPULATION_SIZE = 50; // Кількість особин у популяції
    private static final double MUTATION_RATE = 0.01; // Ймовірність мутації
    private static final int NUM_GENERATIONS = 1000; // Кількість поколінь для еволюції

    // Координати міст
    private static final int[][] CITY_COORDINATES = {
            {0, 0},
            {1, 2},
            {3, 1},
            {5, 2},
            {4, 0}
    };

    public static void main(String[] args) {
        // Ініціалізація початкової популяції
        // Створення початкового набору маршрутів,
        // які охоплюють всі міста і визначаються випадковим чином
        int[][] population = initializePopulation();

        for (int generation = 0; generation < NUM_GENERATIONS; generation++) {
            double[] fitnessValues = calculateFitness(population); // Оцінка придатності (fitness) для кожної особини
            int[][] selectedParents = selectParents(population, fitnessValues); // Вибір особин для розмноження
            int[][] newPopulation = createNewPopulation(selectedParents); // Створення нового покоління
            mutatePopulation(newPopulation); // Мутація нового покоління
            population = newPopulation; // Оновлення популяції

            // Вивід найкращого маршруту у поточному поколінні
            int[] bestRoute = population[0];
            System.out.println("Generation " + generation + ": Best Route Distance - " + calculateRouteDistance(bestRoute));
        }

        // Вивід оптимального маршруту
        int[] bestRoute = population[0];
        System.out.println("Optimal Route: " + Arrays.toString(bestRoute));
        System.out.println("Optimal Route Distance: " + calculateRouteDistance(bestRoute));
    }

    // Ініціалізація початкової популяції
    private static int[][] initializePopulation() {
        int[][] population = new int[POPULATION_SIZE][NUM_CITIES];

        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = generateRandomRoute();
        }

        return population;
    }

    // Розрахунок придатності для кожної особини в популяції
    private static double[] calculateFitness(int[][] population) {
        double[] fitnessValues = new double[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            fitnessValues[i] = 1 / calculateRouteDistance(population[i]);
        }
        return fitnessValues;
    }

    // Вибір батьків для розмноження за методом турніру
    private static int[][] selectParents(int[][] population, double[] fitnessValues) {
        int[][] selectedParents = new int[POPULATION_SIZE][NUM_CITIES];
        Random rand = new Random();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            int parent1Index = rand.nextInt(POPULATION_SIZE);
            int parent2Index = rand.nextInt(POPULATION_SIZE);

            // Вибір кращого з двох батьків на основі придатності
            if (fitnessValues[parent1Index] > fitnessValues[parent2Index]) {
                selectedParents[i] = population[parent1Index];
            } else {
                selectedParents[i] = population[parent2Index];
            }
        }

        return selectedParents;
    }

    // Генерація випадкового маршруту
    private static int[] generateRandomRoute() {
        int[] route = new int[NUM_CITIES];
        for (int i = 0; i < NUM_CITIES; i++) {
            route[i] = i;
        }
        shuffleArray(route);
        return route;
    }


    // Випадкове перемішування масиву
    private static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    // Обчислення відстані для заданого маршруту
    private static double calculateRouteDistance(int[] route) {
        double distance = 0;
        for (int i = 0; i < route.length - 1; i++) {
            int city1 = route[i];
            int city2 = route[i + 1];
            distance += calculateEuclideanDistance(city1, city2);
        }
        // Замикання циклу
        distance += calculateEuclideanDistance(route[NUM_CITIES - 1], route[0]);
        return distance;
    }

    // Обчислення евклідової відстані між двома містами
    private static double calculateEuclideanDistance(int city1, int city2) {
        int[] coord1 = CITY_COORDINATES[city1];
        int[] coord2 = CITY_COORDINATES[city2];
        return Math.sqrt(Math.pow(coord2[0] - coord1[0], 2) + Math.pow(coord2[1] - coord1[1], 2));
    }

    // Допоміжна функція для заповнення дітей значеннями батьків
    private static void fillChildWithParent(int[][] parents, int[][] children, int childIndex, int cityIndex) {
        for (int parentIndex = 0; parentIndex < POPULATION_SIZE; parentIndex++) {
            int parentCity = parents[parentIndex][cityIndex];
            if (!containsCity(children[childIndex], parentCity)) {
                children[childIndex][cityIndex] = parentCity;
                return;
            }
        }
    }

    // Допоміжна функція для перевірки наявності міста в маршруті
    private static boolean containsCity(int[] route, int city) {
        for (int i = 0; i < route.length; i++) {
            if (route[i] == city) {
                return true;
            }
        }
        return false;
    }



    // Змінено функцію кросоверу
    private static int[][] createNewPopulation(int[][] selectedParents) {
        int[][] newPopulation = new int[POPULATION_SIZE][NUM_CITIES];
        Random rand = new Random();

        for (int i = 0; i < POPULATION_SIZE; i += 2) {
            int crossoverPoint1 = rand.nextInt(NUM_CITIES - 1) + 1;
            int crossoverPoint2 = rand.nextInt(NUM_CITIES - 1) + 1;

            // Вибір більшого значення для початку кросоверу
            int startCrossover = Math.min(crossoverPoint1, crossoverPoint2);
            // Вибір меншого значення для кінця кросоверу
            int endCrossover = Math.max(crossoverPoint1, crossoverPoint2);

            // Одноточковий кросовер
            for (int j = startCrossover; j < endCrossover; j++) {
                newPopulation[i][j] = selectedParents[i][j];
                newPopulation[i + 1][j] = selectedParents[i + 1][j];
            }

            // Заповнення решти маршруту дітьми
            for (int j = 0; j < NUM_CITIES; j++) {
                if (j < startCrossover || j >= endCrossover) {
                    fillChildWithParent(selectedParents, newPopulation, i, j);
                    fillChildWithParent(selectedParents, newPopulation, i + 1, j);
                }
            }
        }

        return newPopulation;
    }
    // Змінено функцію мутації
    private static void mutatePopulation(int[][] population) {
        Random rand = new Random();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (rand.nextDouble() < MUTATION_RATE) {
                // Випадковий обмін двох міст
                int mutationPoint1 = rand.nextInt(NUM_CITIES);
                int mutationPoint2 = rand.nextInt(NUM_CITIES);

                int temp = population[i][mutationPoint1];
                population[i][mutationPoint1] = population[i][mutationPoint2];
                population[i][mutationPoint2] = temp;
            }
        }
    }
}








