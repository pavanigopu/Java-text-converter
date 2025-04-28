package com.example.converter.util;

import java.io.File;

public class Util {
    public static String getOutputXmlFileName(File inputFile) {
        return getRoot(inputFile) + ".xml";
    }

    public static String getOutputCsvFileName(File inputFile) {
        return getRoot(inputFile) + ".csv";
    }

    private static String getRoot(File inputFile) {
        String dir = inputFile.getParent() + "/";
        String baseName = inputFile.getName().replaceAll("\\..*$", "");
        return dir + baseName;
    }
}
