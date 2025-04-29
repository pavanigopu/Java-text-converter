package com.example.converter.output;

import com.example.converter.model.Sentence;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class XmlFormatterTest {

    @Test
    public void testXmlWriting() throws IOException {
        File temp = File.createTempFile("test", ".csv");
        try (XmlFormatter formatter = new XmlFormatter(temp.getAbsolutePath())) {
            formatter.writeSentence(new Sentence(Arrays.asList("cat", "dog", "ant", "Mr.")));
        }
        String content = Files.readString(temp.toPath());
        assertTrue(content.contains("<text>"));
        assertTrue(content.contains("<sentence>"));
        assertTrue(content.contains("<word>ant</word>"));
        assertTrue(content.contains("<word>cat</word>"));
        assertTrue(content.contains("<word>dog</word>"));
        assertTrue(content.contains("<word>Mr.</word>"));
        assertTrue(content.contains("</sentence>"));
        assertTrue(content.contains("</text>"));
    }
}
