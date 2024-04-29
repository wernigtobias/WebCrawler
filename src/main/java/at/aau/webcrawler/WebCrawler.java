package at.aau.webcrawler;

import at.aau.services.WebService;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class WebCrawler {

  private final WebCrawlerConfig rootConfiguration;
  private final WebCrawlerOutputFileWriter webCrawlerOutputFileWriter;
  private final Set<String> crawledLinks;

  public WebCrawler(WebCrawlerConfig config) {
    this.rootConfiguration = config;
    this.webCrawlerOutputFileWriter = new WebCrawlerOutputFileWriter(new File("output.md"));
    this.crawledLinks = new HashSet<>();
  }

  public void run() {
    crawl(rootConfiguration);
    webCrawlerOutputFileWriter.writeToOutputFile();
  }

  private void crawl(WebCrawlerConfig configuration) {
    System.out.println("Crawling " + configuration.getUrl() + " with depth " + configuration.getDepth());
    Webpage webpage = loadWebpage(configuration.getUrl());
    if (webpage != null) {
      WebCrawlerResults result = processWebpage(webpage, configuration);
      saveResult(result, configuration);
      processLinks(webpage.getLinks(), configuration);
    }
  }

  Webpage loadWebpage(String url) {
    crawledLinks.add(url);
    return WebService.loadWebpage(url);
  }

  WebCrawlerResults processWebpage(Webpage webpage, WebCrawlerConfig configuration) {
    WebCrawlerResults result = new WebCrawlerResults(configuration);
    result.setHeadings(webpage.getHeadings());
    result.setLinks(webpage.getLinks());
    return result;
  }

  void saveResult(WebCrawlerResults result, WebCrawlerConfig configuration) {
    int currentDepth = rootConfiguration.getDepth() - configuration.getDepth();
    if (currentDepth == 0) {
      webCrawlerOutputFileWriter.setBaseReport(result);
    } else {
      webCrawlerOutputFileWriter.addNestedReport(result, currentDepth);
    }
  }

  void processLinks(Set<String> links, WebCrawlerConfig configuration) {
    if (configuration.getDepth() <= 0) {
      return;
    }
    for (String link : links) {
      processLink(link, configuration);
    }
  }

  void processLink(String link, WebCrawlerConfig configuration) {
    int newDepth = configuration.getDepth() - 1;
    WebCrawlerConfig nestedConfiguration = new WebCrawlerConfig(link, newDepth, configuration.getDomains());
    if (!crawledLinks.contains(link) && nestedConfiguration.verifyConfig()) {
      crawl(nestedConfiguration);
    }
  }
}
