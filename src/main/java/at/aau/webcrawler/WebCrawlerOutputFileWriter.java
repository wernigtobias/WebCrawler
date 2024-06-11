package at.aau.webcrawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WebCrawlerOutputFileWriter {

  public static void writeToOutputFile(String outputFileContents, File outputFile) {
    try (FileWriter fileWriter = new FileWriter(outputFile)) {
      fileWriter.write(outputFileContents);
    } catch (IOException e) {
          System.out.println("[ERROR] Failed writing to output file");
    }
  }
}
