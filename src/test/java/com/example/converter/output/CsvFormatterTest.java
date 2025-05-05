package com.example.converter.output;

import com.example.converter.model.Sentence;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvFormatterTest {

    @Test
    public void testCsvWriting() throws IOException {
        File temp = File.createTempFile("test", ".csv");
        try (CsvFormatter formatter = new CsvFormatter(temp.getAbsolutePath(), 4)) {
            formatter.writeSentence(new Sentence(Arrays.asList("cat", "dog", "ant", "Mr")));
        }
        String content = Files.readString(temp.toPath());
        assertTrue(content.trim().contains("Sentence 1, ant, cat, dog, Mr"));
    }

    @Test
    public void testCsvHeaderLineMatchesMaxWordCount() throws IOException {
        File temp = File.createTempFile("test", ".csv");
        try (CsvFormatter formatter = new CsvFormatter(temp.getAbsolutePath(), 4)) {
            formatter.writeSentence(new Sentence(Arrays.asList("cat", "dog", "ant", "Mr")));
        }

        try (BufferedReader reader = Files.newBufferedReader(temp.toPath())) {
            String header = reader.readLine();
            assertEquals(", Word 1, Word 2, Word 3, Word 4", header);
        }
    }
}
