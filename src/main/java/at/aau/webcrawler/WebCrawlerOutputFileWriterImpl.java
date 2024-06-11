package at.aau.webcrawler;

import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.dto.WebCrawlerPageResult;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WebCrawlerOutputFileWriterImpl {

  private final File outputFile;

  public WebCrawlerOutputFileWriterImpl(File outputFile) {
    this.outputFile = outputFile;
  }


  public static void writeToOutputFile(StringBuilder outputFileContents, File outputFile) {
    try (FileWriter fileWriter = new FileWriter(outputFile)) {
      fileWriter.write(outputFileContents.toString());
    } catch (IOException e) {
          System.out.println("[ERROR] Failed writing to output file");
    }
  }
}
