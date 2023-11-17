package org.ai;
/*
  @author emilia
  @project AI
  @class Perceptron
  @version 1.0.0
  @since 10.11.2023 - 16:43
*/

import java.util.Arrays;

public class Perceptron {
    private final double[] weights; //масив для зберігання ваг персептрона
    private final double learningRate; //швидкість навчання персептрона

    //inputSize - кількість входів персептрона
    //learningRate - швидкість навчання
    public Perceptron(int inputSize, double learningRate) {
        this.weights = new double[inputSize];
        this.learningRate = learningRate;

        // Ініціалізація ваг випадковими значеннями
        for (int i = 0; i < inputSize; i++) {
            weights[i] = Math.random();
        }
    }

    //приймає масив входів та визначає вихід персептрона, використовуючи порогову функцію
    public int predict(double[] inputs) {
        // Сума вагованих вхідних значень
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }

        // Використання порогової функції для визначення виходу
        //Обчислює взважену суму входів за допомогою ваг і визначає, чи перевищує ця сума поріг
        //якщо так, повертає 1, в іншому випадку - 0
        return (sum < 1) ? 0 : 1;
    }

    //проводить навчання персептрона через задану кількість епох
    //під час кожної епохи для кожного тренувального
    //прикладу оновлюються ваги згідно правилу навчання персептрона
    public void train(double[][] trainingData, int[] labels, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < trainingData.length; i++) {
                double[] inputs = trainingData[i];
                int label = labels[i];
                int prediction = predict(inputs);

                // Оновлення ваг за допомогою правила навчання персептрона
                for (int j = 0; j < weights.length; j++) {
                    weights[j] += learningRate * (label - prediction) * inputs[j];
                }
            }
        }
    }

    public static void main(String[] args) {
        // Приклад навчання персептрона для логічної функції AND
        double[][] trainingData = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        int[] labels = {0, 0, 0, 1};

        Perceptron perceptron = new Perceptron(2, 0.1);
        perceptron.train(trainingData, labels, 1000);

        // Перевірка результатів
        for (double[] trainingDatum : trainingData) {
            int prediction = perceptron.predict(trainingDatum);
            System.out.println("Input: " + Arrays.toString(trainingDatum) + " Output: " + prediction);
        }
    }
}
