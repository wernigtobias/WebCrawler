package at.aau.webcrawler.dto;

import java.util.List;
import java.util.Set;

public class WebCrawlerPageResult {

  private final WebCrawlerConfig webCrawlerConfig;

  private List<Heading> headings;
  private Set<String> links;
  private final int depth;
  private final String url;

  public WebCrawlerPageResult(WebCrawlerConfig webCrawlerConfiguration) {
    this.webCrawlerConfig = webCrawlerConfiguration;
    this.depth = webCrawlerConfiguration.getDepth();
    this.url = webCrawlerConfiguration.getUrl();
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

  public void setLinks(Set<String> links) {
    this.links = links;
  }

  public String getUrl() {
    return url;
  }
}
