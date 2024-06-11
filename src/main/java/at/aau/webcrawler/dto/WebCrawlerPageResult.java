package at.aau.webcrawler.dto;
import org.jsoup.select.Elements;

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

  public Set<String> getLinks() {
    return links;
  }

  public void setLinks(Set<String> links) {
    this.links = links;
  }

  public int getDepth() {
    return depth;
  }

  public String getUrl() {
    return url;
  }
}
