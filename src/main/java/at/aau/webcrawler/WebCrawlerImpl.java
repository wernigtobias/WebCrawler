package at.aau.webcrawler;

import at.aau.app.App;
import at.aau.services.WebService;
import at.aau.webcrawler.dto.WebCrawlerConfig;
import at.aau.webcrawler.dto.WebCrawlerPageResult;
import at.aau.webcrawler.dto.Webpage;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawlerImpl {

  private final Set<String> crawledLinks;
  private HashMap<String, WebCrawlerOutputFileWriterImpl> outputFileWriterHashMap;

  public WebCrawlerImpl() {
    this.outputFileWriterHashMap = new HashMap<>();
    this.crawledLinks = new HashSet<>();
  }

  public void run(String[] urls, String[] domains) {
    WebCrawlerExecutor webCrawlerExecutor = new WebCrawlerExecutor();

    for (String url : urls) {
      if(!outputFileWriterHashMap.containsKey(url)) {
        outputFileWriterHashMap.put(url, new WebCrawlerOutputFileWriterImpl(new File("output.md")));
      }

      WebCrawlerConfig webCrawlerConfig = new WebCrawlerConfig(url, 0, url, domains);
      webCrawlerExecutor.submit(() -> crawl(webCrawlerConfig), url);
    }

    String resultOfChildren = webCrawlerExecutor.getResult();
    StringBuilder outputFileBuilder = new StringBuilder();

    for (String url : urls) {
      outputFileBuilder.append(outputFileWriterHashMap.get(url).getOutputFileContent());
      outputFileBuilder.append("\r\n");
    }

    WebCrawlerOutputFileWriterImpl.writeToOutputFile(outputFileBuilder, new File("output.md"));
    webCrawlerExecutor.shutdown();
  }

  public String crawl(WebCrawlerConfig configuration) {
    System.out.println("[Crawler-" + Thread.currentThread().getName() + "-] URL: " + configuration.getUrl() + " Depth: " + configuration.getDepth());

    Webpage webpage = loadWebpage(configuration.getUrl());
    if (webpage != null) {
      WebCrawlerPageResult result = processWebpage(webpage, configuration);
      saveResult(result, configuration);
      processLinks(webpage.getLinks(), configuration);
    }

    return "";
  }

  public Webpage loadWebpage(String url) {
    crawledLinks.add(url);
    return WebService.loadWebpage(url);
  }

  public WebCrawlerPageResult processWebpage(Webpage webpage, WebCrawlerConfig configuration) {
    WebCrawlerPageResult result = new WebCrawlerPageResult(configuration);
    result.setHeadings(webpage.getHeadings());
    result.setLinks(webpage.getLinks());
    return result;
  }

  public void saveResult(WebCrawlerPageResult result, WebCrawlerConfig configuration) {
    if (configuration.getDepth() == 0 ) {
      outputFileWriterHashMap.get(configuration.getRootUrl()).setBaseReport(result);
    } else {
      outputFileWriterHashMap.get(configuration.getRootUrl()).addNestedReport(result);
    }
  }

  public void processLinks(Set<String> links, WebCrawlerConfig configuration) {
    if (configuration.getDepth() >= App.maxDepth) {
      return;
    }
    for (String link : links) {
      processLink(link, configuration);
    }
  }

  public void processLink(String link, WebCrawlerConfig configuration) {
    int newDepth = configuration.getDepth() + 1;
    WebCrawlerConfig nestedConfiguration = new WebCrawlerConfig(link, newDepth, configuration.getRootUrl(),configuration.getDomains());
    if (!crawledLinks.contains(link) && nestedConfiguration.verifyConfig()) {
      crawl(nestedConfiguration);
    }
  }
}
