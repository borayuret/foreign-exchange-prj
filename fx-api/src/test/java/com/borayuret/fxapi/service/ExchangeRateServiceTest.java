package com.borayuret.fxapi.service;

import com.borayuret.fxapi.client.ExchangeRateClient;
import com.borayuret.fxapi.dto.ExchangeRateResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for ExchangeRateService.
 * This test verifies that the service correctly returns exchange rate data
 * based on mocked results from the ExchangeRateClient.
 */
class ExchangeRateServiceTest {

    @Test
    void shouldReturnCorrectExchangeRateResponseDTO() {
        // Arrange: create a mock of ExchangeRateClient
        ExchangeRateClient mockClient = mock(ExchangeRateClient.class);
        ExchangeRateService service = new ExchangeRateService(mockClient);

        // Define test input and expected output
        String from = "USD";
        String to = "EUR";
        double mockedRate = 0.8835;

        // Configure the mock to return a fixed exchange rate
        when(mockClient.getRate(from, to)).thenReturn(mockedRate);

        // Act: call the method under test
        ExchangeRateResponseDTO response = service.getExchangeRate(from, to);

        // Assert: validate the returned DTO
        assertEquals(from, response.getFrom(), "Source currency should match");
        assertEquals(to, response.getTo(), "Target currency should match");
        assertEquals(mockedRate, response.getRate(), 0.0001, "Exchange rate should match mocked value");
    }
}
