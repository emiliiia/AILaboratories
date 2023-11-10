package org.ai;
/*
  @author emilia
  @project AI
  @class Text
  @version 1.0.0
  @since 10.11.2023 - 12:13
*/

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;

public class TextCorrectionAlgorithm {

    public static String correctText(String inputText) {
        // метрика Levenshtein для порівняння варіантів
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        String[] tokens = inputText.split(" "); // Розбити текст на токени

        StringBuilder correctedText = new StringBuilder();

        //Levenshtein для вибору найкращого варіанта виправлення для кожного токена на основі мінімальної відстані
        // між оригінальним токеном і кандидатами на виправлення, де результатом є текст, який містить виправлені
        // токени з пробілами між ними
        for (String token : tokens) {
            // Відправити кожен токен на виправлення
            String correctedToken = correctToken(token);

            // Використовувати Levenshtein відстань для визначення, який варіант є найближчим до оригінального токену
            int minDistance = Integer.MAX_VALUE;
            String bestCorrection = correctedToken;

            for (String candidateCorrection : getCandidateCorrections(token)) {
                int distance = levenshteinDistance.apply(token, candidateCorrection);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestCorrection = candidateCorrection;
                }
            }

            correctedText.append(bestCorrection).append(" ");
        }

        return correctedText.toString().trim();
    }

    public static String correctToken(String token) {

        // Видалення зайвих пробілів на початку та в кінці токену
        token = token.trim();

        if (token.equals("usa")) {
            token = "USA";
        }

        if (token.equals("Dr.")) {
            token = "Doctor";
        }

        token = token.replaceAll("cant", "can't");

        return token;
    }


    public static List<String> getCandidateCorrections(String token) {
        List<String> candidates = new ArrayList<>();

        // Додавання можливих варіантів виправлення на основі лінгвістичних правил

        if (token.equals("teh")) {
            candidates.add("the");
        }

        if (token.equals("writting")) {
            candidates.add("writing");
        }

        if (token.equals("recieve")) {
            candidates.add("receive");
        }

        if (token.equals("Teh")) {
            candidates.add("The");
        }

        return candidates;
    }

    public static void main(String[] args) {
        String inputText = "Dr. Johnson cant live in usa . " +
                "Teh writting on the wall was so clear that I knew I would recieve the message without any doubt.";

        String correctedText = correctText(inputText);

        System.out.println("Input Text: " + inputText);
        System.out.println("Corrected Text: " + correctedText);
    }
}

