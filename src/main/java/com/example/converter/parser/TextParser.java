package com.example.converter.parser;

import com.example.converter.model.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser {
    private static final Set<String> ABBREVIATIONS = Set.of("Mr.", "Mrs.", "Dr.", "Prof.", "Jr.", "Sr.", "St.", "e.g.", "i.e.");
    private static final Pattern END_PUNCTUATION = Pattern.compile("([.!?])\\s+");
    private static final Pattern WORD_PATTERN = Pattern.compile("[\\p{L}\\p{M}\\p{N}']+\\.?");

    private final StringBuilder buffer = new StringBuilder();
    private final Consumer<Sentence> consumer;

    public TextParser(Consumer<Sentence> consumer) {
        this.consumer = consumer;
    }

    public void processChunk(String chunk) {
        buffer.append(chunk).append(" ");
        String text = buffer.toString();
        Matcher matcher = END_PUNCTUATION.matcher(text);
        int lastEnd = 0;
        while (matcher.find()) {
            int splitPos = matcher.end();
            String candidate = text.substring(lastEnd, splitPos).trim();
            if (!isAbbreviation(candidate)) {
                List<String> words = extractWords(candidate);
                if (!words.isEmpty()) {
                    consumer.accept(new Sentence(words));
                }
                lastEnd = splitPos;
            }
        }
        buffer.setLength(0);
        buffer.append(text.substring(lastEnd).trim());
    }

    private boolean isAbbreviation(String sentence) {
        for (String abbr : ABBREVIATIONS) {
            if (sentence.endsWith(abbr)) {
                return true;
            }
        }
        return false;
    }

    private List<String> extractWords(String sentence) {
        Matcher matcher = WORD_PATTERN.matcher(sentence);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            String word = matcher.group().trim();
            if (!word.isEmpty()) {
                if(word.endsWith(".") && !ABBREVIATIONS.contains(word)) {
                    word = word.substring(0, word.length() - 1);
                }
                words.add(word);
            }
        }
        return words;
    }

    public void flush() {
        String leftOver = buffer.toString().trim();
        if (!leftOver.isEmpty()) {
            List<String> words = extractWords(leftOver);
            if (!words.isEmpty()) {
                consumer.accept(new Sentence(words));
            }
        }
    }
}
