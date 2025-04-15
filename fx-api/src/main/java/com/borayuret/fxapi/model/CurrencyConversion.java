package com.borayuret.fxapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a currency conversion transaction.
 * Each record corresponds to a single conversion request and its result.
 */
@Entity
public class CurrencyConversion {

    @Id
    private UUID transactionId;

    private double amount;

    private String fromCurrency;

    private String toCurrency;

    private double rate;

    private double convertedAmount;

    private LocalDateTime timestamp;

    /**
     * Default constructor for JPA
     */
    public CurrencyConversion() {}

    public CurrencyConversion(UUID transactionId, double amount, String fromCurrency, String toCurrency,
                              double rate, double convertedAmount, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
        this.convertedAmount = convertedAmount;
        this.timestamp = timestamp;
    }

    // --- Getters and Setters ---

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
