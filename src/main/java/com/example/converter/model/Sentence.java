package com.example.converter.model;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public record Sentence(List<String> words) {
    private static List<String> sentenceWords;
    public Sentence {
        words.sort(Comparator.comparing((String s) -> s.toLowerCase(Locale.ROOT)).thenComparing(s -> Character.isLowerCase(s.charAt(0)) ? 0 : 1));
        sentenceWords = words;
    }

    public List<String> getWords() {
        return sentenceWords;
    }
}
