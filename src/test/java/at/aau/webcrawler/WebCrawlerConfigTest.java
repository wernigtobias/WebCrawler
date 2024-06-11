package at.aau.webcrawler;
import at.aau.webcrawler.dto.WebCrawlerConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WebCrawlerConfigTest {

  @Test
  public void testVerifyConfig_ValidConfig() {
    String url = "https://example.com";
    int depth = 2;
    String[] domains = {"example.com", "subdomain.example.com"};
    WebCrawlerConfig config = new WebCrawlerConfig(url, depth, url, domains);
    assertTrue(config.verifyConfig());
  }

  @Test
  public void testVerifyConfig_InvalidURL() {
    String url = "invalid-url";
    int depth = 2;
    String[] domains = {"example.com"};
    WebCrawlerConfig config = new WebCrawlerConfig(url, depth, url, domains);
    assertFalse(config.verifyConfig());
  }

  @Test
  public void testVerifyConfig_InvalidDepth() {
    String url = "https://example.com";
    int depth = -1;
    String[] domains = {"example.com"};
    WebCrawlerConfig config = new WebCrawlerConfig(url, depth, url, domains);
    assertFalse(config.verifyConfig());
  }

  @Test
  public void testVerifyConfig_InvalidDomains() {
    String url = "https://example.com";
    int depth = 2;
    String[] domains = {"invalid-domain"};
    WebCrawlerConfig config = new WebCrawlerConfig(url, depth, url, domains);
    assertFalse(config.verifyConfig());
  }

  @Test
  public void testVerifyConfig_NullDomains() {
    String url = "https://example.com";
    int depth = 2;
    String[] domains = null;
    WebCrawlerConfig config = new WebCrawlerConfig(url, depth, url, domains);
    assertFalse(config.verifyConfig());
  }
}
