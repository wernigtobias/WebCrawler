package at.aau.webcrawler.dto;

public class Heading {
    private String text;
    private final int order;

    public Heading(String text, int order){
        this.text = text;
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getOrder() {
        return order;
    }
}
