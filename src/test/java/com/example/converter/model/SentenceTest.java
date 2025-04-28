package com.example.converter.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SentenceTest {

    @Test
    public void testWordsSorting() {
        List<String> words = Arrays.asList("Hello", "world", "hello", "hello world", "ant", "cat");
        Sentence sentence = new Sentence(words);
        List<String> sortedWords = Arrays.asList("ant", "cat", "hello", "Hello", "hello world", "world");
        assertEquals(sortedWords, sentence.getWords());
    }

    @Test
    public void testSentenceEquality() {
        Sentence s1 = new Sentence(Arrays.asList("apple", "banana"));
        Sentence s2 = new Sentence(Arrays.asList("banana", "apple"));

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    public void testSentenceAsMapKey() {
        Map<Sentence, String> map = new HashMap<>();
        Sentence s1 = new Sentence(Arrays.asList("apple", "banana"));
        Sentence s2 = new Sentence(Arrays.asList("banana", "apple"));
        map.put(s1, "Fruits");
        assertEquals("Fruits", map.get(s2));
    }

}
