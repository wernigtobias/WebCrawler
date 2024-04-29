package at.aau.webcrawler.dto;

import at.aau.webcrawler.WebCrawlerVerifier;

public class WebCrawlerConfig {
  private final String url;
  private final int depth;
  private final String[] domains;

  public WebCrawlerConfig(String url, int depth, String[] domains) {
    this.url = url;
    this.depth = depth;
    this.domains = domains;
  }

  public String getUrl() {
    return url;
  }

  public int getDepth() {
    return depth;
  }

  public String[] getDomains() {
    return domains;
  }

  public boolean verifyConfig(){
    if (url == null || domains == null) {
      return false;
    }
    return WebCrawlerVerifier.verifyURL(url, domains) && WebCrawlerVerifier.verifyDepth(depth) && WebCrawlerVerifier.verifyDomains(domains);
  }

}
