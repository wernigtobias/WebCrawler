package at.aau.services;

import at.aau.webcrawler.Webpage;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebService {
  public static Webpage loadWebpage(String url) {
    try {
      Document document = Jsoup.connect(url).get();
      return new Webpage(document);
    } catch (HttpStatusException e) {
      if (e.getStatusCode() == 404) {
        return null;
      }
    } catch (IOException ie) {
      System.out.println("[ERROR] Failed connecting");
    }
    return null;
  }
}
