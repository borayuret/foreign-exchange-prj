package com.borayuret.fxapi.service;

import com.borayuret.fxapi.client.FixerExchangeRateClient;
import com.borayuret.fxapi.dto.BulkCurrencyConversionResponseDTO;
import com.borayuret.fxapi.dto.CurrencyConversionRequestDTO;
import com.borayuret.fxapi.dto.CurrencyConversionResponseDTO;
import com.borayuret.fxapi.model.CurrencyConversion;
import com.borayuret.fxapi.repository.CurrencyConversionRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for handling currency conversion and retrieving transaction history.
 */
@Service
public class CurrencyConversionService {

    private final FixerExchangeRateClient exchangeRateClient;
    private final CurrencyConversionRepository conversionRepository;

    public CurrencyConversionService(FixerExchangeRateClient exchangeRateClient,
                                     CurrencyConversionRepository conversionRepository) {
        this.exchangeRateClient = exchangeRateClient;
        this.conversionRepository = conversionRepository;
    }

    /**
     * Converts a given amount from one currency to another,
     * persists the conversion to the database, and returns a response DTO.
     *
     * @param request the conversion request containing amount, from and to currencies
     * @return the conversion result with transaction ID
     */
    public CurrencyConversionResponseDTO convertCurrency(CurrencyConversionRequestDTO request) {
        double rate = exchangeRateClient.getRate(request.getFrom(), request.getTo());
        double convertedAmount = request.getAmount() * rate;
        UUID transactionId = UUID.randomUUID();
        LocalDateTime timestamp = LocalDateTime.now();

        CurrencyConversion conversion = new CurrencyConversion(
                transactionId,
                request.getAmount(),
                request.getFrom(),
                request.getTo(),
                rate,
                convertedAmount,
                timestamp
        );

        conversionRepository.save(conversion);

        return new CurrencyConversionResponseDTO(convertedAmount, transactionId);
    }

    /**
     * Retrieves a paginated list of conversions filtered by transaction ID or date.
     * At least one parameter must be provided.
     *
     * @param transactionId optional transaction ID
     * @param date optional transaction date
     * @param pageable pagination info
     * @return a paginated list of matching conversion records
     */
    public Page<CurrencyConversion> getConversionHistory(UUID transactionId, LocalDate date, Pageable pageable) {
        if (transactionId != null) {
            Optional<CurrencyConversion> result = conversionRepository.findByTransactionId(transactionId);
            return result
                    .map(conversion -> new PageImpl<CurrencyConversion>(List.of(conversion), pageable, 1))
                    .orElse(new PageImpl<CurrencyConversion>(List.of(), pageable, 0));
        } else if (date != null) {
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(LocalTime.MAX);
            return conversionRepository.findByTimestampBetween(start, end, pageable);
        } else {
            throw new IllegalArgumentException("Either transactionId or date must be provided.");
        }
    }

    /**
     * Processes a CSV file containing multiple currency conversion requests.
     *
     * @param file CSV file with columns: amount, from, to
     * @return list of conversion results
     */
    public BulkCurrencyConversionResponseDTO convertFromCsv(MultipartFile file) {
        List<CurrencyConversionResponseDTO> results = new ArrayList<>();

        try (
                CSVParser parser = CSVFormat.DEFAULT
                        .withHeader("amount", "from", "to")
                        .withSkipHeaderRecord()
                        .parse(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
        ) {
            for (CSVRecord record : parser) {
                double amount = Double.parseDouble(record.get("amount"));
                String from = record.get("from");
                String to = record.get("to");

                CurrencyConversionRequestDTO request = new CurrencyConversionRequestDTO(amount, from, to);
                CurrencyConversionResponseDTO result = convertCurrency(request);
                results.add(result);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to process CSV file: " + e.getMessage(), e);
        }

        return new BulkCurrencyConversionResponseDTO(results);
    }
}
