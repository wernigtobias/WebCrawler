package at.aau.webcrawler;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebCrawlerOutputFileWriterTest {

    private static final File outputFile = new File("output.md");

    @BeforeEach
    void init() throws IOException {
        Files.deleteIfExists(outputFile.toPath());
    }

    @Test
    void writeToFileTest() throws IOException {
        String content = "fileContent";
        WebCrawlerOutputFileWriter.writeToOutputFile(content, outputFile);
        assertEquals("fileContent", Files.readString(outputFile.toPath()));
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(outputFile.toPath());
    }

}
