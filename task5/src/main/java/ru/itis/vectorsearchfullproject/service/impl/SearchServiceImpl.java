package ru.itis.vectorsearchfullproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.itis.vectorsearchfullproject.service.LemmatizerService;
import ru.itis.vectorsearchfullproject.service.ReaderService;
import ru.itis.vectorsearchfullproject.service.SearchService;
import ru.itis.vectorsearchfullproject.utils.Helper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    public static final Integer PAGES_COUNT = 196;

    private final LemmatizerService lemmatizerService;
    private final ReaderService readerService;

//    Основной метод поиска
    @SneakyThrows
    @Override
    public List<String> findPagesByQuery(String query) {
//        Лемматизируем исходный запрос
        List<String> searchQueryLemmas = lemmatizerService.lemmatize(query.toLowerCase());
//        Получаем информацию о составленном инвертированном индексе
        Map<String, List<Integer>> invertedIndices = readerService.readAllLemmas();
        Map<String, Integer> lemmaToCount = lemmaToCount(searchQueryLemmas);
        Map<Integer, Double> pageIndexToScore = new HashMap<>();

        for (int index = 1; index <= PAGES_COUNT; index++) {
            String filePath = String.format("/Users/daniilbogomolov/Desktop/search-course/tf_idf_freq/lemmas/lemmas_%d.txt", index);
            List<String> lemmasTF_IDFinfo = Files.readAllLines(Path.of(filePath));
//            Для каждой страницы собираем векторы поискового запроса и вектор страницы
            List<Double> searchVector = new ArrayList<>();
            List<Double> pageLemmaVector = new ArrayList<>();
            for (String lemmaInfo : lemmasTF_IDFinfo) {
                String[] lemmaData = lemmaInfo.split(" ");
                String lemma = lemmaData[0];
                if (searchQueryLemmas.contains(lemma)) {
                    double idf = Double.parseDouble(lemmaData[3]);
                    double lemmaSearchScore = ((double) lemmaToCount.get(lemma) / invertedIndices.get(lemma).size()) * idf;
                    searchVector.add(lemmaSearchScore);
                    pageLemmaVector.add(idf);
                } else {
                    searchVector.add(0.0);
                    pageLemmaVector.add(0.0);
                }
            }
            Double cosineSimilarity = Helper.cosineSimilarity(searchVector, pageLemmaVector);
            pageIndexToScore.put(index, cosineSimilarity);
        }
        List<Integer> pagesIndices = pageIndexToScore.entrySet().stream()
                .filter(entry -> !entry.getValue().equals(0.0))
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return readerService.readPageLinkByIndices(pagesIndices);
    }

    private Map<String, Integer> lemmaToCount(List<String> searchQueryLemmas) {
        Map<String, Integer> lemmaToCount = new HashMap<>();
        for (String lemma : searchQueryLemmas) {
            if (lemmaToCount.containsKey(lemma)) {
                lemmaToCount.put(lemma, lemmaToCount.get(lemma) + 1);
            } else {
                lemmaToCount.put(lemma, 1);
            }
        }
        return lemmaToCount;
    }
}
