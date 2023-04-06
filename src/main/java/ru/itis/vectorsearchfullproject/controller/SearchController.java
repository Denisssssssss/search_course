package ru.itis.vectorsearchfullproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.vectorsearchfullproject.service.SearchService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/search")
    public List<String> searchPages(@RequestParam("query") String searchText) {
        return searchService.findPagesByQuery(searchText);
    }
}
