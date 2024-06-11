package at.aau.webcrawler;

import at.aau.app.App;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WebCrawlerVerifierTest {

    @Test
    public void testVerifyURL_ValidURL_ValidDomain() {
        String url = "https://www.example.com";
        String[] domains = {"example.com"};
        assertTrue(WebCrawlerVerifier.verifyURL(url, domains));
    }

    @Test
    public void testVerifyURL_ValidURL_InvalidDomain() {
        String url = "https://www.example.com";
        String[] domains = {"invalid.com"};
        assertFalse(WebCrawlerVerifier.verifyURL(url, domains));
    }

    @Test
    public void testVerifyURL_InvalidURL_ValidDomain() {
        String url = "invalidurl";
        String[] domains = {"example.com"};
        assertFalse(WebCrawlerVerifier.verifyURL(url, domains));
    }

    @Test
    public void testVerifyDepth_PositiveDepth() {
        int depth = 5;
        App.setMaxDepth(6);
        assertTrue(WebCrawlerVerifier.verifyDepth(depth));
    }

    @Test
    public void testVerifyDepth_ZeroDepth() {
        int depth = 0;
        assertFalse(WebCrawlerVerifier.verifyDepth(depth));
    }

    @Test
    public void testVerifyDomains_ValidDomains() {
        String[] domains = {"example.com", "sub.example.com"};
        assertTrue(WebCrawlerVerifier.verifyDomains(domains));
    }

    @Test
    public void testVerifyDomains_InvalidDomains() {
        String[] domains = {"example.com", "example,org"};
        assertFalse(WebCrawlerVerifier.verifyDomains(domains));
    }

    @Test
    public void testVerifyDomain_ValidDomain() {
        String domain = "example.com";
        assertTrue(WebCrawlerVerifier.verifyDomain(domain));
    }

    @Test
    public void testVerifyDomain_InvalidDomain() {
        String domain = "example,org";
        assertFalse(WebCrawlerVerifier.verifyDomain(domain));
    }

    @Test
    public void testContainsValidDomain_ValidDomain() {
        String url = "https://www.example.com";
        String[] domains = {"example.com"};
        assertTrue(WebCrawlerVerifier.containsValidDomain(url, domains));
    }

    @Test
    public void testContainsValidDomain_InvalidDomain() {
        String url = "https://www.example.com";
        String[] domains = {"invalid.com"};
        assertFalse(WebCrawlerVerifier.containsValidDomain(url, domains));
    }
}