package com.example.converter.parser;

import com.example.converter.model.Sentence;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextParserTest {

    @Test
    public void testMultiplePunctuationParsing() {
        String input = "Mary had a little lamb. Peter called for the wolf! Cinderella likes shoes?";
        List<Sentence> results = new ArrayList<>();
        TextParser parser = new TextParser(results::add);

        parser.processChunk(input);
        parser.flush();

        assertEquals(3, results.size());
        assertTrue(results.getFirst().words().contains("Mary"));
        assertTrue(results.get(1).words().contains("Peter"));
        assertTrue(results.getLast().words().contains("Cinderella"));
    }

    @Test
    public void testSentenceSplitOverMultipleLines() {
        List<Sentence> results = new ArrayList<>();
        TextParser parser = new TextParser(results::add);

        parser.processChunk("This is a sentence");
        parser.processChunk(" that continues over");
        parser.processChunk(" multiple lines!");
        parser.flush();

        assertEquals(1, results.size());
        assertTrue(results.getFirst().words().contains("multiple"));
    }

    @Test
    public void testMultilingualSupport() {
        List<Sentence> results = new ArrayList<>();
        TextParser parser = new TextParser(results::add);

        parser.processChunk("停在那儿, 你这肮脏的掠夺者! Olá, mundo? Привіт, світ.");
        parser.flush();

        assertEquals(3, results.size());
        assertTrue(results.getFirst().words().contains("你这肮脏的掠夺者"));
    }

    @Test
    public void testSentenceWithAbbreviation() {
        List<Sentence> results = new ArrayList<>();
        TextParser parser = new TextParser(results::add);

        parser.processChunk("Mr. John Smith invited by Mr. Rohit!");
        parser.flush();

        assertEquals(1, results.size());
        assertTrue(results.getFirst().words().contains("Mr."));
    }

    @Test
    public void testNoSentenceBoundary() {
        String input = "This text has no punctuation or clear end";
        List<Sentence> results = new ArrayList<>();
        TextParser parser = new TextParser(results::add);
        parser.processChunk(input);
        parser.flush();
        assertEquals(1, results.size());
    }
}
