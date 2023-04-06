package ru.itis.vectorsearchfullproject.service;

import java.util.List;

public interface LemmatizerService {

    List<String> lemmatize(String text);
}
