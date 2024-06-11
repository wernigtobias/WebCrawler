package at.aau.webcrawler.dto;

import org.jsoup.select.Elements;

import java.util.Set;

public interface Webpage {
    Elements getHeadings();
    Set<String> getLinks();
}
