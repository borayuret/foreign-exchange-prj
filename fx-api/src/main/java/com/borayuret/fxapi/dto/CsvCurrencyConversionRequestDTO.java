package com.borayuret.fxapi.dto;

/**
 * DTO representing a single row in the uploaded CSV file.
 * Each row defines one currency conversion request with amount, source, and target currencies.
 */
public class CsvCurrencyConversionRequestDTO {

    /**
     * The amount to be converted.
     */
    private double amount;

    /**
     * The currency code to convert from (e.g. "USD").
     */
    private String from;

    /**
     * The currency code to convert to (e.g. "EUR").
     */
    private String to;

    public CsvCurrencyConversionRequestDTO() {}

    public CsvCurrencyConversionRequestDTO(double amount, String from, String to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
