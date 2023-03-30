package ru.itis.vectorsearchfullproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.itis.vectorsearchfullproject.dto.InvertedIndexDataDto;
import ru.itis.vectorsearchfullproject.service.ReaderService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {

    private final ObjectMapper mapper;

//    Метод получения лемм из построенного инвертированного индекса
    @SneakyThrows
    @Override
    public Map<String, List<Integer>> readAllLemmas() {
        return Files.lines(Path.of("/Users/daniilbogomolov/Desktop/search-course/inverted_index.txt"))
                .map(line -> mapJsonToObject(line, InvertedIndexDataDto.class))
                .collect(Collectors.toMap(InvertedIndexDataDto::getWord, InvertedIndexDataDto::getPagesNumbers));
    }

//    Метод получения ссылок на фильмы по их индексам из файла index.txt
    @SneakyThrows
    @Override
    public List<String> readPageLinkByIndices(List<Integer> indices) {
        List<String> pageLine = Files.readAllLines(Path.of("/Users/daniilbogomolov/Desktop/search-course/index.txt"));
        List<String> resultPages = new ArrayList<>();
        for (String line : pageLine) {
            String[] lineData = line.split(" ");
            int pageIndex = Integer.parseInt(lineData[0]);
            if (indices.contains(pageIndex)) {
                resultPages.add(lineData[1]);
            }
        }
        return resultPages;
    }

    @SneakyThrows
    private <T> T mapJsonToObject(String value, Class<T> clazz) {
        return mapper.readValue(value, clazz);
    }
}
