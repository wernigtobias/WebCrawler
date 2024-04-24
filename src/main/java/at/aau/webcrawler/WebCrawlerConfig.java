package at.aau.webcrawler;

import at.aau.webcrawler.WebCrawlerVerifier;

import java.util.regex.Pattern;

public class WebCrawlerConfig {
  private String url;
  private int depth;
  private String[] domains;

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
    return WebCrawlerVerifier.verifyURL(url, domains) && WebCrawlerVerifier.verifyDepth(depth) && WebCrawlerVerifier.verifyDomains(domains);
  }

}
