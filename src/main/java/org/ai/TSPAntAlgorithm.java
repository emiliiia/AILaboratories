package org.ai;
/*
  @author emilia
  @project AI
  @class Ant
  @version 1.0.0
  @since 21.11.2023 - 12:47
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Ant {
    private int currentCity; //поточне місто, в якому знаходиться мураха.
    private final List<Integer> tour; //список міст, які вже відвідані мурахою
    private final boolean[] visitedCities; //масив для відстеження відвіданих міст

    public Ant(int numCities) {
        this.currentCity = new Random().nextInt(numCities);
        this.tour = new ArrayList<>();
        this.visitedCities = new boolean[numCities];
        tour.add(currentCity);
        visitedCities[currentCity] = true;
    }

    public int getCurrentCity() {
        return currentCity;
    }

    public List<Integer> getTour() {
        return tour;
    }

    //переміщує мураху в наступне місто та оновлює відповідні дані
    public void move(int nextCity) {
        currentCity = nextCity;
        tour.add(currentCity);
        visitedCities[currentCity] = true;
    }

    //перевіряє, чи мураха вже відвідала певне місто
    public boolean hasVisitedCity(int city) {
        return visitedCities[city];
    }
}

class AntColony {
    private final int numAnts;
    private final int numCities;
    private final double[][] pheromones; //матриця феромонів для кожного шляху між містами
    private final double[][] distances; //матриця відстаней між містами
    private final List<Ant> ants;

    public AntColony(int numAnts, int numCities, double[][] distances) {
        this.numAnts = numAnts;
        this.numCities = numCities;
        this.distances = distances;
        this.pheromones = new double[numCities][numCities];
        this.ants = new ArrayList<>();


        //ініціалізація феромонів
        double initialPheromone = 1.0 / numCities;
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] = initialPheromone;
            }
        }
    }

    //запускає мурахів для проходження маршруту через всі міста
    public void runAnts() {
        ants.clear();
        for (int i = 0; i < numAnts; i++) {
            //кожна мураха ініціалізується випадковим початковим містом
            ants.add(new Ant(numCities));
        }

        //рух мурів
        for (int step = 0; step < numCities - 1; step++) {
            for (Ant ant : ants) {
                int nextCity = selectNextCity(ant);
                ant.move(nextCity);
            }
        }

        // оновлення рівнів феромонів відповідно до маршрутів, які пройшли мурахи
        updatePheromones(ants);
    }

    //вибирає наступне місто для мурахи на основі феромонів та відстаней
    private int selectNextCity(Ant ant) {
        //реалізація стратегії руху мурів, використовуючи інформацію про феромони та відстані
        //використовується простий випадковий вибір
        List<Integer> unvisitedCities = new ArrayList<>();
        for (int i = 0; i < numCities; i++) {
            if (!ant.hasVisitedCity(i)) {
                unvisitedCities.add(i);
            }
        }

        // Випадковий вибір наступного міста
        Random random = new Random();
        return unvisitedCities.get(random.nextInt(unvisitedCities.size()));
    }

    //оновлює рівні феромонів на основі пройдених мурахами маршрутів
    private void updatePheromones(List<Ant> ants) {
        //реалізація стратегії оновлення феромонів, наприклад, на основі довжини маршруту мурів
        //використовується просте оновлення
        double evaporationRate = 0.5;

        //випаровування феромонів
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromones[i][j] *= (1 - evaporationRate);
            }
        }

        //депозит феромонів на основі маршрутів мурів
        for (Ant ant : ants) {
            List<Integer> tour = ant.getTour();
            double pheromoneDeposit = 1.0 / calculateTourLength(tour);
            for (int i = 0; i < numCities - 1; i++) {
                int fromCity = tour.get(i);
                int toCity = tour.get(i + 1);
                pheromones[fromCity][toCity] += pheromoneDeposit;
                pheromones[toCity][fromCity] += pheromoneDeposit; // Припускаємо симетричну TSP
            }
        }
    }

    //обчислює довжину маршруту мурів
    private double calculateTourLength(List<Integer> tour) {
        double length = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            int fromCity = tour.get(i);
            int toCity = tour.get(i + 1);
            length += distances[fromCity][toCity];
        }
        //додавання відстанні від останнього міста до початкового міста
        length += distances[tour.get(tour.size() - 1)][tour.get(0)];
        return length;
    }

    //повертає найкращий маршрут, знайдений мурахами
    public List<Integer> getBestTour() {
        int bestAntIndex = 0;
        double bestTourLength = Double.MAX_VALUE;

        for (int i = 0; i < numAnts; i++) {
            List<Integer> tour = ants.get(i).getTour();
            double tourLength = calculateTourLength(tour);

            if (tourLength < bestTourLength) {
                bestTourLength = tourLength;
                bestAntIndex = i;
            }
        }

        return ants.get(bestAntIndex).getTour();
    }
}

public class TSPAntAlgorithm {
    public static void main(String[] args) {
        int numCities = 5;
        int numAnts = 10;

        //випадкова матриця відстаней
        double[][] distances = {
                {0, 1, 2, 3, 4},
                {1, 0, 5, 6, 7},
                {2, 5, 0, 8, 9},
                {3, 6, 8, 0, 10},
                {4, 7, 9, 10, 0}
        };

        AntColony antColony = new AntColony(numAnts, numCities, distances);

        //запустити мурахів для певної кількості ітерацій
        int numIterations = 100;
        for (int i = 0; i < numIterations; i++) {
            antColony.runAnts();
        }

        //отримання найкращого маршруту, знайденого мурахами
        List<Integer> bestTour = antColony.getBestTour();

        //виведення найкращий маршрут
        System.out.println("The best route: " + bestTour);
    }
}
