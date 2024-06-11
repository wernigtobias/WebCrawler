package at.aau.webcrawler.dto;

import java.util.List;
import java.util.Set;

public class WebCrawlerPageResult {

  private final WebCrawlerConfig webCrawlerConfig;
  private List<Heading> headings;


  public WebCrawlerPageResult(WebCrawlerConfig webCrawlerConfiguration) {
    this.webCrawlerConfig = webCrawlerConfiguration;
  }

  public void setHeadings(List<Heading> headings) {
    this.headings = headings;
  }

  public WebCrawlerConfig getWebCrawlerConfiguration() {
    return webCrawlerConfig;
  }

  public List<Heading> getHeadings() {
    return headings;
  }
}
