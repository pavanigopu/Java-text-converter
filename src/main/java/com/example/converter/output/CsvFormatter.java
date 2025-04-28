package com.example.converter.output;

import com.example.converter.model.Sentence;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFormatter implements AutoCloseable {
    private final BufferedWriter writer;
    private int sentenceCount = 1;

    public CsvFormatter(String filePath) throws IOException {
        writer = new BufferedWriter(new FileWriter(filePath));
        writer.write("Sentence");
        for (int i = 1; i <= 30; i++) {
            writer.write(", Word" + i);
        }
        writer.write("\n");
    }

    public void writeSentence(Sentence sentence) throws IOException {
        writer.write("Sentence " + sentenceCount++);
        for (String word : sentence.words()) {
            writer.write(", " + word);
        }
        writer.write("\n");
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
