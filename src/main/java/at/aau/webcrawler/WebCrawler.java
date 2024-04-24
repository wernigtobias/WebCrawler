package at.aau.webcrawler;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
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

    Webpage webpage= getWebsite(configuration);
    if (webpage == null) {
      return;
    }
    WebCrawlerResults result = new WebCrawlerResults(configuration);
    result.setHeadings(webpage.getHeadings());
    Set<String> links = webpage.getLinks();
    result.setLinks(links);
    saveResult(result, configuration);
    processLinks(links, configuration);
  }

  private Webpage getWebsite(WebCrawlerConfig configuration) {
    String url = configuration.getUrl();
    crawledLinks.add(url);
    try {
      Document document = Jsoup.connect(url).get();
      return new Webpage(document);
    } catch (HttpStatusException e) {
      if (e.getStatusCode() == 404) {
        int currentDepth = getCurrentDepth(configuration);
        webCrawlerOutputFileWriter.addBrokenLinkReport(configuration, currentDepth);
      }
    } catch (IOException ie) {
      ie.printStackTrace();
    }
    return null;
  }

  private void saveResult(WebCrawlerResults result, WebCrawlerConfig configuration) {
    int currentDepth = getCurrentDepth(configuration);
    if (currentDepth == 0) {
      webCrawlerOutputFileWriter.setBaseReport(result);
    } else {
      webCrawlerOutputFileWriter.addNestedReport(result, currentDepth);
    }
  }

  private void processLinks(Set<String> links, WebCrawlerConfig configuration) {
    if (configuration.getDepth() <= 0) {
      return;
    }
    for (String link : links) {
      String[] domains = configuration.getDomains();
      int newDepth = configuration.getDepth()-1;
      WebCrawlerConfig nestedConfiguration = new WebCrawlerConfig(link, newDepth, domains);
      if (!crawledLinks.contains(link) && nestedConfiguration.verifyConfig()) {
        crawl(nestedConfiguration);
      }
    }
  }

  private int getCurrentDepth(WebCrawlerConfig configuration) {
    return rootConfiguration.getDepth() - configuration.getDepth();
  }
}
