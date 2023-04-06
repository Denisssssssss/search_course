package ru.itis.vectorsearchfullproject.config;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class LemmatizerConfig {

    @Bean
    public StanfordCoreNLP lemmatizerNLP() {
        Properties properties = new Properties();
        properties.put("annotators", "tokenize, ssplit, pos, lemma");
        return new StanfordCoreNLP(properties);
    }
}
