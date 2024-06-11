package at.aau.translator;

import okhttp3.OkHttpClient;

import java.io.IOException;

public interface TranslatorService {
    boolean validateLanguage(String language);
    String translate(String text, String targetLanguage) throws IOException, TranslatorServiceException;
    public void setHttpClient(OkHttpClient httpClient);
}
