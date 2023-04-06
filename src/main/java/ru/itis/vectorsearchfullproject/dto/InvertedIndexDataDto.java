package ru.itis.vectorsearchfullproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvertedIndexDataDto {

    private String word;

    @JsonProperty("inverted_array")
    private List<Integer> pagesNumbers;
}
