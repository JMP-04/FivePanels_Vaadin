package com.fivepanels.application.model.domain.user.misc;

import com.fivepanels.application.model.foundation.assertion.Assertion;
import com.fivepanels.application.model.foundation.exception.AssertionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Language {
    private String language;
    private static final Set<String> validLanguages = new HashSet<>();

    public Language(String language) {

        loadValidLanguages();
        setLanguage(language);
    }

    public void loadValidLanguages() {

        if (validLanguages.isEmpty()) {

            try (InputStream is = getClass().getResourceAsStream("/languages.txt")) {

                Assertion.isNotNull(is, "is");

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                    String language;
                    while ((language = reader.readLine()) != null) {

                        validLanguages.add(language.trim().toLowerCase());
                    }

                    System.out.println("Loaded " + validLanguages.size() + " languages.");
                }
            } catch (IOException e) {

                throw new AssertionException("Failed to load languages from file" + e.getMessage());
            }
        }
    }

    public boolean isValidLanguage(String language) {

        return validLanguages.contains(language.trim().toLowerCase());
    }

    public String getLanguage() {

        return language;
    }

    public void setLanguage(String language) {

        if (!isValidLanguage(language)) {

            throw new AssertionException("Invalid language: " + language);
        }

        this.language = language;
    }
}