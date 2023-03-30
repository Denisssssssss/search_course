package ru.itis.vectorsearchfullproject.utils;

import java.util.List;

public class Helper {

//    Вспомогательный метод подсчета меры косинусного сходства для векторов
    public static Double cosineSimilarity(List<Double> vector1, List<Double> vector2) {
        Double numenator = 0.0;
        Double denomFirstMultiplier = 0.0;
        Double denomSecondMultiplier = 0.0;

        for (int i = 0; i < vector1.size(); i++) {
            numenator += vector1.get(i) * vector2.get(i);
            denomFirstMultiplier += vector1.get(i) * vector1.get(i);
            denomSecondMultiplier += vector2.get(i) * vector2.get(i);
        }

        Double denomenator = Math.sqrt(denomFirstMultiplier) * Math.sqrt(denomSecondMultiplier);

        if (numenator.equals(0.0) || denomenator.equals(0.0)) {
            return 0.0;
        }
        return numenator / denomenator;
    }
}
