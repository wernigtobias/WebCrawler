package at.aau.services;

import at.aau.webcrawler.dto.Webpage;
import at.aau.webcrawler.dto.WebpageImpl;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebService {

    public static Webpage loadWebpage(String url) throws WebServiceException {
        try {
            Document document = Jsoup.connect(url).get();
            return new WebpageImpl(document);
        } catch (HttpStatusException e) {
            System.out.println("[ERROR] Failed to load Webpage " + url + " because of StatusCode: " + e.getStatusCode());
            throw new WebServiceException("[ERROR] Failed to load Webpage " + url + " because of StatusCode: " + e.getStatusCode());
        } catch (IOException ie) {
            System.out.println("[ERROR] Failed connecting: " + ie.getMessage());
            throw new WebServiceException("[ERROR] Failed connecting: " + ie.getMessage());
        }
    }
}
