package at.aau.webcrawler;

import at.aau.webcrawler.dto.Heading;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeadingTest {

    @Test
    public void testHeadingGetters() {
        Heading heading = new Heading("Überschrift 1", 1);

        assertEquals(heading.getText(), "Überschrift 1");
        assertEquals(heading.getOrder(), 1);
    }

    @Test
    public void testHeadingSetText() {
        Heading heading = new Heading("Überschrift 1", 1);

        assertEquals(heading.getText(), "Überschrift 1");
        heading.setText("Verändert 1");
        assertEquals(heading.getText(), "Verändert 1");
    }
}
