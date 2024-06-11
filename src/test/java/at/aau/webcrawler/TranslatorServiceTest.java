package at.aau.webcrawler;

import at.aau.translator.TranslatorService;
import at.aau.translator.TranslatorServiceException;
import at.aau.translator.TranslatorServiceImpl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TranslatorServiceTest {
    private static OkHttpClient mockedHttpClient;
    private static TranslatorService translatorService;

    private static Response mockedResponse;
    private static Call mockedCall;
    private static ResponseBody mockedResponseBody;


    @BeforeAll
    static void setUp() throws IOException {
        translatorService = new TranslatorServiceImpl();

        mockedHttpClient = Mockito.mock(OkHttpClient.class);
        translatorService.setHttpClient(mockedHttpClient);

        mockedResponse = Mockito.mock(Response.class);
        mockedCall = Mockito.mock(Call.class);
        mockedResponseBody = Mockito.mock(ResponseBody.class);

        Mockito.when(mockedHttpClient.newCall(Mockito.any())).thenReturn(mockedCall);
        Mockito.when(mockedCall.execute()).thenReturn(mockedResponse);
        Mockito.when(mockedResponse.body()).thenReturn(mockedResponseBody);
    }

    @AfterAll
    static void cleanup() {
        mockedHttpClient = null;
        translatorService = null;
        mockedCall = null;
        mockedResponse = null;
        mockedResponseBody = null;
    }


    @Test
    void validateLanguageTest_validInput() {
        assertTrue(translatorService.validateLanguage("german"));
    }

    @Test
    void validateLanguageTest_invalidInput() {
        assertFalse(translatorService.validateLanguage("Huttisch"));
    }

    @Test
    void getAbbreviationOfLanguageTest_validInput() {
        assertEquals("de", TranslatorServiceImpl.getAbbreviationOfLanguage("german"));
    }

    @Test
    void getAbbreviationOfLanguageTest_invalidInput() {
        assertThrows(
                IllegalArgumentException.class,
                () -> TranslatorServiceImpl.getAbbreviationOfLanguage("Huttisch")
        );
    }

    @Test
    void translateTest_validInput() throws IOException, TranslatorServiceException {
        initForTranslateTest_validInput();

        assertEquals("Hello", translatorService.translate("Hallo", "english"));
    }

    @Test
    void translateTest_invalidInputNull() {
        assertThrows(
                TranslatorServiceException.class,
                () -> translatorService.translate(null, "german")
        );
    }

    @Test
    void translateTest_invalidInputNotARealLanguage() {
        assertThrows(
                TranslatorServiceException.class,
                () -> translatorService.translate("Testeingabe", "Huttisch")
        );
    }

    @Test
    void translateTest_TranslatorServiceException() {
        initForTranslateTest_invalidInput();
        assertThrows(
                TranslatorServiceException.class,
                () -> translatorService.translate("Testeingabe", "german")
        );
    }

    private void initForTranslateTest_invalidInput() {
        Mockito.when(mockedResponse.isSuccessful()).thenReturn(false);
    }

    private void initForTranslateTest_validInput() throws IOException {
        Mockito.when(mockedResponse.isSuccessful()).thenReturn(true);
        Mockito.when(mockedResponseBody.string()).thenReturn("{ \"data\": { \"translatedText\": \"Hello\" }}");
    }
}
