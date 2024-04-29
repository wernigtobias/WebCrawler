package at.aau.webcrawler;

import java.util.regex.Pattern;
import java.util.Arrays;

public class WebCrawlerVerifier {
  private static final String URL_REGEX = "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)";
  private static final String DOMAIN_REGEX = "^(?:[-A-Za-z0-9]+\\.)+[A-Za-z]{2,6}$";

  public static boolean verifyURL(String url, String[] domains) {
    return Pattern.matches(URL_REGEX, url) && containsValidDomain(url, domains);
  }

  public static boolean verifyDepth(int depth) {
    return depth > 0;
  }

  public static boolean verifyDomains(String[] domains) {
    if (domains == null) {
      return false;
    }
    return Arrays.stream(domains).allMatch(WebCrawlerVerifier::verifyDomain);
  }

  static boolean verifyDomain(String domain) {
    return Pattern.matches(DOMAIN_REGEX, domain);
  }

  static boolean containsValidDomain(String url, String[] domains) {
    return Arrays.stream(domains).anyMatch(url::contains);
  }
}
