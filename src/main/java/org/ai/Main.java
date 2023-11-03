package org.ai;

import org.ai.fuzzy.FuzzyRules;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter engine speed (rpm): ");
        double rpm = scanner.nextDouble();

        System.out.print("Enter the change in motor rotation speed (dRPM): ");
        double dRPM = scanner.nextDouble();

        // Визначення сигналу керування подачею пального
        double fuelControlSignal = FuzzyRules.applyRule(rpm, dRPM);

        System.out.println("Fuel supply control signal: " + fuelControlSignal);

        scanner.close();
    }
}

//Вхідні змінні:
//Швидкість обертання двигуна (rpm) - діапазон [0, 8000]
//Зміна швидкості обертання двигуна (dRPM) - діапазон [-2000, 2000]
//Вихідна змінна:
//Сигнал керування подачею пального (Fuel Control Signal) - діапазон [0, 1]

