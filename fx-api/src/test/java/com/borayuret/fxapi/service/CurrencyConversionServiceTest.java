package com.borayuret.fxapi.service;

import com.borayuret.fxapi.client.ExchangeRateClient;
import com.borayuret.fxapi.dto.CurrencyConversionRequestDTO;
import com.borayuret.fxapi.dto.CurrencyConversionResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for CurrencyConversionService.
 * Ensures that the conversion logic correctly calculates the amount
 * and generates a transaction ID.
 */
class CurrencyConversionServiceTest {

    @Test
    void shouldReturnCorrectConvertedAmountAndTransactionId() {
        // Arrange: mock the ExchangeRateClient dependency
        ExchangeRateClient mockClient = mock(ExchangeRateClient.class);
        CurrencyConversionService service = new CurrencyConversionService(mockClient);

        // Prepare request and mock rate
        CurrencyConversionRequestDTO request = new CurrencyConversionRequestDTO(100.0, "USD", "TRY");
        double mockRate = 38.0;

        // Define mock behavior
        when(mockClient.getRate("USD", "TRY")).thenReturn(mockRate);

        // Act: perform conversion
        CurrencyConversionResponseDTO response = service.convertCurrency(request);

        // Assert: verify converted amount and non-null transaction ID
        assertNotNull(response.getTransactionId(), "Transaction ID should not be null");
        assertEquals(3800.0, response.getConvertedAmount(), 0.0001, "Converted amount should match expected value");
    }
}
