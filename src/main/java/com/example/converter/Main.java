package com.example.converter;

import com.example.converter.output.CsvFormatter;
import com.example.converter.output.XmlFormatter;
import com.example.converter.parser.TextParser;
import com.example.converter.util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {

    public static final int FOUR_KB = 4096;

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.err.println("Please provide an input file.");
            return;
        }

        File inputFile = new File(args[0]);
        if (!inputFile.exists()) {
            System.err.println("Input file not found: " + inputFile.getPath());
            return;
        }
        String outputXmlFileName = Util.getOutputXmlFileName(inputFile);
        String outputCsvFileName = Util.getOutputCsvFileName(inputFile);

        try (
                XmlFormatter xmlOut = new XmlFormatter(outputXmlFileName);
                CsvFormatter csvOut = new CsvFormatter(outputCsvFileName);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8), FOUR_KB)
        ) {
            TextParser parser = new TextParser(sentence -> {
                try {
                    xmlOut.writeSentence(sentence);
                    csvOut.writeSentence(sentence);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            char[] buffer = new char[FOUR_KB];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                parser.processChunk(new String(buffer, 0, read));
            }
            parser.flush();
        }

        System.out.println("Streaming completed. Output written to " + outputXmlFileName + " and " + outputCsvFileName);
    }
}
