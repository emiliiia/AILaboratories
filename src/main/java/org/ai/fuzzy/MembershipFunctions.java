package org.ai.fuzzy;
/*
  @author emilia
  @project AI
  @class MembershipFunctions
  @version 1.0.0
  @since 03.11.2023 - 10:59
*/

public class MembershipFunctions {
    //наскільки конкретне значення належить до певного нечіткого множини
    // функції приналежності для швидкості обертання двигуна
    public static double lowSpeed(double rpm) {
        return Math.max(0, 1 - rpm / 4000.0);
    }

    public static double mediumSpeed(double rpm) {
        return Math.max(0, Math.min((rpm - 3000) / 2000.0, (6000 - rpm) / 2000.0));
    }

    public static double highSpeed(double rpm) {
        return Math.max(0, (rpm - 4000) / 4000.0);
    }

    // функції приналежності для вхідної змінної зміна швидкості обертання двигуна
    public static double negativeChange(double dRPM) {
        return Math.max(0, -dRPM / 1000.0);
    }

    public static double noChange(double dRPM) {
        return Math.max(0, Math.min(dRPM / 1000.0, -dRPM / 1000.0));
    }

    public static double positiveChange(double dRPM) {
        return Math.max(0, dRPM / 1000.0);
    }

    // функції приналежності для вихідної змінної сигнал керування подачею пального
    public static double lowFuelControlSignal(double fuelControlSignal) {
        return Math.max(0, 1 - fuelControlSignal);
    }

    public static double highFuelControlSignal(double fuelControlSignal) {
        return Math.max(0, fuelControlSignal);
    }
}
