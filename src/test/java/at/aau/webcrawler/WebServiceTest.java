package at.aau.webcrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebServiceTest {

    private Document documentMock;
    private Connection connectionMock;

    @Test
    void testGetDocument() {
        try (MockedStatic<Jsoup> mockedStatic = Mockito.mockStatic(Jsoup.class)) {
            initForSuccessTest();
            assertEquals(documentMock, Jsoup.connect("url").get());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void initForSuccessTest() throws IOException {
        documentMock = Mockito.mock(Document.class);
        connectionMock = Mockito.mock(Connection.class);
        Mockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connectionMock);
        Mockito.when(connectionMock.get()).thenReturn(documentMock);
    }
}
