package at.aau.webcrawler;

import java.util.regex.Pattern;

public class WebCrawlerVerifier {

  public static boolean verifyURL(String url, String[] domains){

    String urlRegex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
    boolean urlHasGivenDomain = false;
    for(String domainToCrawl: domains){
      if(url.contains(domainToCrawl)){
        urlHasGivenDomain = true;
      }
    }
    return Pattern.matches(urlRegex, url) && urlHasGivenDomain;
  }

  public static boolean verifyDepth(int depth) {
    return depth > 0;
  }

  public static boolean verifyDomains(String[] domains) {
    for (String domain : domains) {
      if (!verifyDomain(domain)) {
        return false;
      }
    }
    return true;
  }

  public static boolean verifyDomain(String domain){
    String domainRegex = "^(?:[-A-Za-z0-9]+\\.)+[A-Za-z]{2,6}$";
    return Pattern.matches(domainRegex, domain);
  }

}
