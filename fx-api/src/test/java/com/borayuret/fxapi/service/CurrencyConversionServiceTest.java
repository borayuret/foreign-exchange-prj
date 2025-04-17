package com.borayuret.fxapi.service;

import com.borayuret.fxapi.client.FixerExchangeRateClient;
import com.borayuret.fxapi.dto.BulkCurrencyConversionResponseDTO;
import com.borayuret.fxapi.dto.CurrencyConversionRequestDTO;
import com.borayuret.fxapi.dto.CurrencyConversionResponseDTO;
import com.borayuret.fxapi.model.CurrencyConversion;
import com.borayuret.fxapi.repository.CurrencyConversionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        // Arrange
        FixerExchangeRateClient mockClient = mock(FixerExchangeRateClient.class);
        CurrencyConversionRepository mockRepo = mock(CurrencyConversionRepository.class);
        CurrencyConversionService service = new CurrencyConversionService(mockClient, mockRepo);

        CurrencyConversionRequestDTO request = new CurrencyConversionRequestDTO(100.0, "USD", "TRY");
        double mockRate = 38.0;

        when(mockClient.getRate("USD", "TRY")).thenReturn(mockRate);

        // Act
        CurrencyConversionResponseDTO response = service.convertCurrency(request);

        // Assert
        assertNotNull(response.getTransactionId(), "Transaction ID should not be null");
        assertEquals(3800.0, response.getConvertedAmount(), 0.0001, "Converted amount should be correct");
    }

    @Test
    void shouldReturnPageWithSingleConversion_WhenTransactionIdIsGiven() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        CurrencyConversion mockConversion = new CurrencyConversion();
        mockConversion.setTransactionId(transactionId);

        CurrencyConversionRepository mockRepo = mock(CurrencyConversionRepository.class);
        when(mockRepo.findByTransactionId(transactionId)).thenReturn(Optional.of(mockConversion));

        CurrencyConversionService service = new CurrencyConversionService(mock(FixerExchangeRateClient.class), mockRepo);
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<CurrencyConversion> result = service.getConversionHistory(transactionId, null, pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(transactionId, result.getContent().get(0).getTransactionId());
    }

    @Test
    void shouldReturnConversionsByDate_WhenDateIsProvided() {
        // Arrange
        LocalDate date = LocalDate.of(2025, 4, 15);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        Pageable pageable = PageRequest.of(0, 10);

        CurrencyConversion mockConversion = new CurrencyConversion();
        mockConversion.setTimestamp(start.plusHours(12)); // Noon

        List<CurrencyConversion> mockList = List.of(mockConversion);
        Page<CurrencyConversion> mockPage = new PageImpl<>(mockList, pageable, mockList.size());

        CurrencyConversionRepository mockRepo = mock(CurrencyConversionRepository.class);
        when(mockRepo.findByTimestampBetween(start, end, pageable)).thenReturn(mockPage);

        CurrencyConversionService service = new CurrencyConversionService(mock(FixerExchangeRateClient.class), mockRepo);

        // Act
        Page<CurrencyConversion> result = service.getConversionHistory(null, date, pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(mockConversion.getTimestamp(), result.getContent().get(0).getTimestamp());
    }

    @Test
    void shouldThrowException_WhenNoFilterIsProvided() {
        // Arrange
        CurrencyConversionRepository mockRepo = mock(CurrencyConversionRepository.class);
        CurrencyConversionService service = new CurrencyConversionService(mock(FixerExchangeRateClient.class), mockRepo);
        Pageable pageable = PageRequest.of(0, 10);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            service.getConversionHistory(null, null, pageable);
        });
    }


    @Test
    void shouldProcessCsvFileAndReturnConversionResults() {
        // Arrange
        String csvContent = "amount,from,to\n" +
                "100,USD,TRY\n" +
                "250,EUR,USD\n";
        MockMultipartFile csvFile = new MockMultipartFile(
                "file",
                "conversions.csv",
                "text/csv",
                csvContent.getBytes()
        );

        FixerExchangeRateClient mockClient = mock(FixerExchangeRateClient.class);
        CurrencyConversionRepository mockRepo = mock(CurrencyConversionRepository.class);
        CurrencyConversionService service = new CurrencyConversionService(mockClient, mockRepo);

        when(mockClient.getRate("USD", "TRY")).thenReturn(30.0);
        when(mockClient.getRate("EUR", "USD")).thenReturn(1.1);

        // simulating db access
        when(mockRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BulkCurrencyConversionResponseDTO result = service.convertFromCsv(csvFile);

        // Assert
        assertNotNull(result);
        List<CurrencyConversionResponseDTO> responses = result.getResults();
        assertEquals(2, responses.size());

        CurrencyConversionResponseDTO first = responses.get(0);
        assertEquals(3000.0, first.getConvertedAmount(), 0.01);

        CurrencyConversionResponseDTO second = responses.get(1);
        assertEquals(275.0, second.getConvertedAmount(), 0.01);

        // Transaction IDs are random, but must not be null
        assertNotNull(first.getTransactionId());
        assertNotNull(second.getTransactionId());
    }
}

