package ru.itis.vectorsearchfullproject.service.impl;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import lombok.RequiredArgsConstructor;
import ru.itis.vectorsearchfullproject.service.LemmatizerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LemmatizerServiceImpl implements LemmatizerService {

    private final StanfordCoreNLP nlpLemmatizer;

//    Метод лемматизации текста
    @Override
    public List<String> lemmatize(String text) {
        Annotation annotation = new Annotation(text);
        List<String> lemmasList = new ArrayList<>();
        nlpLemmatizer.annotate(annotation);
//        Разделяем текст на предложения
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
//            Разделяем предложения на отдельные слова
            List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
//            Для каждого слова получаем его лемматизированную форму
            List<String> lemmas = tokens.stream()
                    .map(token -> token.get(CoreAnnotations.LemmaAnnotation.class))
                    .collect(Collectors.toList());
            lemmasList.addAll(lemmas);
        }
        return lemmasList;
    }
}
