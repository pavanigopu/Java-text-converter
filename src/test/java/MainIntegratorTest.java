import com.example.converter.Main;
import com.example.converter.util.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainIntegratorTest {

    @AfterEach
    public void cleanup() throws IOException {
        Files.deleteIfExists(Path.of("output.xml"));
        Files.deleteIfExists(Path.of("output.csv"));
    }

    @Test
    public void testMainCreatesOutput() throws IOException {
        File tempInput = File.createTempFile("testinput", ".txt");
        try (FileWriter fw = new FileWriter(tempInput)) {
            fw.write("Hello World. Bojour monde! Hola mundo?");
        }
        Main.main(new String[]{tempInput.getAbsolutePath()});

        String outputXmlFileName = Util.getOutputXmlFileName(tempInput);
        String outputCsvFileName = Util.getOutputCsvFileName(tempInput);

        Path xmlPath = Path.of(outputXmlFileName);
        Path csvPath = Path.of(outputCsvFileName);
        assertTrue(Files.exists(xmlPath));
        assertTrue(Files.exists(csvPath));

        String xmlContent = Files.readString(xmlPath);
        String csvContent = Files.readString(csvPath);

        assertTrue(xmlContent.contains("<word>Hello</word>"));
        assertTrue(csvContent.contains("Sentence 1, Hello, World"));

        tempInput.delete();
    }

    @Test
    public void testLargeFileProcessing() throws IOException {
        File tempInput = File.createTempFile("largeinput", ".txt");
        try (FileWriter fw = new FileWriter(tempInput)) {
            for(int i=0; i<100000; i++) {
                fw.write("Sentence number " + i + ". ");
            }
        }
        Main.main(new String[]{tempInput.getAbsolutePath()});

        String outputCsvFileName = Util.getOutputCsvFileName(tempInput);
        Path csvPath = Path.of(outputCsvFileName);

        assertTrue(Files.exists(csvPath));

        String csvContent = Files.readString(csvPath);

        assertTrue(csvContent.contains("Sentence 1, 0, number, Sentence"));

        tempInput.delete();
    }

    @Test
    public void testMainNoArguments() {
        // Should just print usage
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }
}
