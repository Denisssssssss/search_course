package ru.itis.vectorsearchfullproject.service;

import java.util.List;

public interface SearchService {

    List<String> findPagesByQuery(String query);
}
