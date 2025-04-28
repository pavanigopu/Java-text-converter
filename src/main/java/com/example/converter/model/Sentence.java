package com.example.converter.model;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public record Sentence(List<String> words) {
    private static List<String> sentenceWords;
    public Sentence {
        words.sort(Comparator.comparing((String s) -> s.toLowerCase(Locale.ROOT)).thenComparing(s -> Character.isLowerCase(s.charAt(0)) ? 0 : 1));
        sentenceWords = words;
    }

    public List<String> getWords() {
        return sentenceWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sentence(List<String> words1))) return false;
        return Objects.equals(words, words1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }
}
