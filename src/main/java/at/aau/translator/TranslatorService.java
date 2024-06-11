package at.aau.translator;

import java.io.IOException;

public interface TranslatorService {
    boolean validateLanguage(String language);
    String translate(String text, String targetLanguage) throws IOException, TranslatorServiceException;
}
