package at.aau.webcrawler.dto;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Set;

public class WebCrawlerPageResult {

  private final WebCrawlerConfig webCrawlerConfig;

  private Elements headings;
  private Set<String> links;

  public WebCrawlerPageResult(WebCrawlerConfig webCrawlerConfiguration) {
    this.webCrawlerConfig = webCrawlerConfiguration;
  }

  public void setHeadings(Elements headings) {
    this.headings = headings;
  }

  public WebCrawlerConfig getWebCrawlerConfiguration() {
    return webCrawlerConfig;
  }

  public Elements getHeadings() {
    return headings;
  }

  public Set<String> getLinks() {
    return links;
  }

  public void setLinks(Set<String> links) {
    this.links = links;
  }
}
