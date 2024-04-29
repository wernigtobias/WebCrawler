package at.aau.webcrawler;

import at.aau.services.WebService;
import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.dto.WebCrawlerResults;
import at.aau.webcrawler.dto.Webpage;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class WebCrawlerImpl implements WebCrawler{

  private final WebCrawlerConfig rootConfiguration;
  private final WebCrawlerOutputFileWriterImpl webCrawlerOutputFileWriterImpl;
  private final Set<String> crawledLinks;

  public WebCrawlerImpl(WebCrawlerConfig config) {
    this.rootConfiguration = config;
    this.webCrawlerOutputFileWriterImpl = new WebCrawlerOutputFileWriterImpl(new File("output.md"));
    this.crawledLinks = new HashSet<>();
  }

  public void run() {
    crawl(rootConfiguration);
    webCrawlerOutputFileWriterImpl.writeToOutputFile();
  }

  public void crawl(WebCrawlerConfig configuration) {
    System.out.println("[Crawler] URL: " + configuration.getUrl() + " Depth: " + configuration.getDepth());
    Webpage webpage = loadWebpage(configuration.getUrl());
    if (webpage != null) {
      WebCrawlerResults result = processWebpage(webpage, configuration);
      saveResult(result, configuration);
      processLinks(webpage.getLinks(), configuration);
    }
  }

  public Webpage loadWebpage(String url) {
    crawledLinks.add(url);
    return WebService.loadWebpage(url);
  }

  public WebCrawlerResults processWebpage(Webpage webpage, WebCrawlerConfig configuration) {
    WebCrawlerResults result = new WebCrawlerResults(configuration);
    result.setHeadings(webpage.getHeadings());
    result.setLinks(webpage.getLinks());
    return result;
  }

  public void saveResult(WebCrawlerResults result, WebCrawlerConfig configuration) {
    int currentDepth = rootConfiguration.getDepth() - configuration.getDepth();
    if (currentDepth == 0) {
      webCrawlerOutputFileWriterImpl.setBaseReport(result);
    } else {
      webCrawlerOutputFileWriterImpl.addNestedReport(result, currentDepth);
    }
  }

  public void processLinks(Set<String> links, WebCrawlerConfig configuration) {
    if (configuration.getDepth() <= 0) {
      return;
    }
    for (String link : links) {
      processLink(link, configuration);
    }
  }

  public void processLink(String link, WebCrawlerConfig configuration) {
    int newDepth = configuration.getDepth() - 1;
    WebCrawlerConfig nestedConfiguration = new WebCrawlerConfig(link, newDepth, configuration.getDomains());
    if (!crawledLinks.contains(link) && nestedConfiguration.verifyConfig()) {
      crawl(nestedConfiguration);
    }
  }
}
