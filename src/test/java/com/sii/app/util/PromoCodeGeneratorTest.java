package com.sii.app.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PromoCodeGeneratorTest {

    @Test
    public void generate_ReturnsNonNullString() {
        // When
        String generatedCode = PromoCodeGenerator.generate();

        // Then
        assertNotNull(generatedCode);
    }

    @Test
    public void generate_ReturnsStringOfCorrectLength() {
        // Given
        int expectedMinLength = 3;
        int expectedMaxLength = 24;

        // When
        String generatedCode = PromoCodeGenerator.generate();

        // Then
        assertTrue(generatedCode.length() >= expectedMinLength);
        assertTrue(generatedCode.length() <= expectedMaxLength);
    }

    @Test
    public void generate_ReturnsUniqueCodes() {
        // Given
        String code1 = PromoCodeGenerator.generate();
        String code2 = PromoCodeGenerator.generate();

        // Then
        assertNotEquals(code1, code2);
    }

    @Test
    public void generate_ReturnsOnlyValidCharacters() {
        // Given
        String generatedCode = PromoCodeGenerator.generate();

        // Then
        assertTrue(generatedCode.matches("[a-zA-Z0-9]+"));
    }
}