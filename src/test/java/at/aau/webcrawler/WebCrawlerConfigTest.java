package at.aau.webcrawler;
import at.aau.app.App;
import at.aau.webcrawler.dto.WebCrawlerConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class WebCrawlerConfigTest {

  private MockedStatic<WebCrawlerVerifier> webCrawlerVerifierMock;

  @BeforeEach
  void setUp() {
    webCrawlerVerifierMock = mockStatic(WebCrawlerVerifier.class);
  }

  @AfterEach
  void tearDown() {
    webCrawlerVerifierMock.close();
  }

  @Test
  public void testVerifyConfig_ValidConfig() {
    String url = "https://example.com";
    int depth = 2;
    String[] domains = {"example.com", "subdomain.example.com"};

    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyURL(url, domains)).thenReturn(true);
    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyDepth(depth)).thenReturn(true);
    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyDomains(domains)).thenReturn(true);


    WebCrawlerConfig config = new WebCrawlerConfig(url, depth, url, domains);
    assertTrue(config.verifyConfig());
  }

  @Test
  public void testVerifyConfig_InvalidURL() {
    String url = "invalid-url";
    int depth = 2;
    String[] domains = {"example.com"};

    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyURL(url, domains)).thenReturn(false);
    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyDepth(depth)).thenReturn(true);
    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyDomains(domains)).thenReturn(true);

    WebCrawlerConfig config = new WebCrawlerConfig(url, depth, url, domains);
    assertFalse(config.verifyConfig());
  }

  @Test
  public void testVerifyConfig_InvalidDepth() {
    String url = "https://example.com";
    int depth = -1;
    String[] domains = {"example.com"};

    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyURL(url, domains)).thenReturn(true);
    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyDepth(depth)).thenReturn(false);
    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyDomains(domains)).thenReturn(true);

    WebCrawlerConfig config = new WebCrawlerConfig(url, depth, url, domains);
    assertFalse(config.verifyConfig());
  }

  @Test
  public void testVerifyConfig_InvalidDomains() {
    String url = "https://example.com";
    int depth = 2;
    String[] domains = {"invalid-domain"};

    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyURL(url, domains)).thenReturn(true);
    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyDepth(depth)).thenReturn(true);
    webCrawlerVerifierMock.when(() -> WebCrawlerVerifier.verifyDomains(domains)).thenReturn(false);

    WebCrawlerConfig config = new WebCrawlerConfig(url, depth, url, domains);
    assertFalse(config.verifyConfig());
  }
}
