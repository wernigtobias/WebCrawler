package at.aau.webcrawler;

import org.jsoup.select.Elements;
import java.util.Set;

public class WebCrawlerResults {

  private final WebCrawlerConfig webCrawlerConfig;

  private Elements headings;
  private Set<String> links;

  public WebCrawlerResults(WebCrawlerConfig webCrawlerConfiguration) {
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
