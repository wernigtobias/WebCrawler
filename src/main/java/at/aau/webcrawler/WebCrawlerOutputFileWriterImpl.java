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
  private final StringBuilder outputFileContent = new StringBuilder();

  public WebCrawlerOutputFileWriterImpl(File outputFile) {
    this.outputFile = outputFile;
  }

  public void setBaseReport(WebCrawlerPageResult webCrawlerResult) {
    WebCrawlerConfig webCrawlerConfiguration = webCrawlerResult.getWebCrawlerConfiguration();
    appendLine("input: <a>", webCrawlerConfiguration.getUrl(), "</a>");
    appendLine("depth:", String.valueOf(webCrawlerConfiguration.getDepth()));
    appendLine("crawled domains:");
    appendArrayItems(webCrawlerConfiguration.getDomains());
    appendLine("summary:");
    appendHeadings(webCrawlerResult);
  }

  public void addNestedReport(WebCrawlerPageResult nestedWebCrawlerResult) {
    WebCrawlerConfig webCrawlerConfiguration = nestedWebCrawlerResult.getWebCrawlerConfiguration();
    String crawledUrl = webCrawlerConfiguration.getUrl();
    appendLine(createDepthIndent(webCrawlerConfiguration.getDepth()) + " link to <a>", crawledUrl, "</a>");
    appendHeadings(nestedWebCrawlerResult);
  }

  public void addBrokenLinkReport(WebCrawlerConfig configuration, int depth) {
    String brokenLink = createDepthIndent(depth) + " broken link<a>" + configuration.getUrl() + "</a>\n";
    outputFileContent.append(brokenLink);
  }

  private String createDepthIndent(int depth) {
    StringBuilder indent = new StringBuilder();
    indent.append("-".repeat(Math.max(0, 2 * depth)));
    if (depth > 0) {
      indent.append(">");
    }
    return indent.toString();
  }

  private void appendLine(String... parts) {
    for (String part : parts) {
      outputFileContent.append(part);
    }
    outputFileContent.append("\n");
  }

  private void appendArrayItems(String[] items) {
    for (String item : items) {
      appendLine("<br>" + item);
    }
  }

  private void appendHeadings(WebCrawlerPageResult webCrawlerResult) {
    Elements headings = webCrawlerResult.getHeadings();
    for (Element heading : headings) {
      outputFileContent.append(formatHeading(heading, webCrawlerResult.getWebCrawlerConfiguration().getDepth()));
    }
    outputFileContent.append("\n");
  }

  private String formatHeading(Element heading, int depth) {
    StringBuilder markdownHeading = new StringBuilder();
    int headingLevel = Integer.parseInt(heading.tagName().substring(1));
    markdownHeading.append("\n");
    markdownHeading.append("#".repeat(Math.max(0, headingLevel)));
    markdownHeading.append(" ");
    markdownHeading.append(createDepthIndent(depth));
    markdownHeading.append(" ");
    markdownHeading.append(heading.text());
    markdownHeading.append("\n");
    return markdownHeading.toString();
  }

  public static void writeToOutputFile(StringBuilder outputFileContents, File outputFile) {
    try (FileWriter fileWriter = new FileWriter(outputFile)) {
      fileWriter.write(outputFileContents.toString());
    } catch (IOException e) {
          System.out.println("[ERROR] Failed writing to output file");
    }
  }

  public StringBuilder getOutputFileContent() {
    return outputFileContent;
  }
}
