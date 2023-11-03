package org.ai.fuzzy;
/*
  @author emilia
  @project AI
  @class FuzzyRules
  @version 1.0.0
  @since 03.11.2023 - 10:59
*/

public class FuzzyRules {
    public static double applyRule(double rpm, double dRPM) {

        //якщо швидкість обертання низька та зміна швидкості додатня,
        //то сигнал керування подачею пального повинен бути високим

        double lowSpeed = MembershipFunctions.lowSpeed(rpm);
        double mediumSpeed = MembershipFunctions.mediumSpeed(rpm);
        double highSpeed = MembershipFunctions.highSpeed(rpm);

        double negativeChange = MembershipFunctions.negativeChange(dRPM);
        double noChange = MembershipFunctions.noChange(dRPM);
        double positiveChange = MembershipFunctions.positiveChange(dRPM);

        double lowFuelControlSignal = 0.0;
        double highFuelControlSignal = 0.0;

        // Правила бази знань
        //якщо швидкість обертання низька і зміна швидкості позитивна,
        // то сигнал керування подачею пального встановлюється на низький рівень
        if (lowSpeed > 0 && positiveChange > 0) {
            lowFuelControlSignal = Math.min(lowSpeed, positiveChange);
        }
        if (mediumSpeed > 0 && noChange > 0) {
            lowFuelControlSignal = Math.min(mediumSpeed, noChange);
        }
        if (mediumSpeed > 0 && negativeChange > 0) {
            highFuelControlSignal = Math.min(mediumSpeed, negativeChange);
        }
        if (highSpeed > 0 && negativeChange > 0) {
            highFuelControlSignal = Math.min(highSpeed, negativeChange);
        }

        // Розрахунок вихідного сигналу керування подачею пального
        double numerator = (lowFuelControlSignal * 0.2) + (highFuelControlSignal * 0.8);
        double denominator = lowFuelControlSignal + highFuelControlSignal;

        return numerator / denominator;
    }
}
