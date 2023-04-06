package ru.itis.vectorsearchfullproject.service;

import java.util.List;
import java.util.Map;

public interface ReaderService {

    Map<String, List<Integer>> readAllLemmas();

    List<String> readPageLinkByIndices(List<Integer> indices);
}
