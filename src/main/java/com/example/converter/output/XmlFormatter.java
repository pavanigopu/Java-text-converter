package com.example.converter.output;

import com.example.converter.model.Sentence;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class XmlFormatter implements AutoCloseable {
    private final BufferedWriter writer;

    public XmlFormatter(String filePath) throws IOException {
        writer = new BufferedWriter(new FileWriter(filePath));
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<text>\n");
    }

    public void writeSentence(Sentence sentence) throws IOException {
        writer.write("<sentence>");
        for (String word : sentence.words()) {
            writer.write("<word>" + word + "</word>");
        }
        writer.write("</sentence>\n");
    }

    @Override
    public void close() throws IOException {
        writer.write("</text>");
        writer.close();
    }
}
