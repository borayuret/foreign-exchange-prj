package com.borayuret.fxapi.dto;

/**
 * Request DTO for currency conversion.
 * Contains the amount to convert, the source currency, and the target currency.
 */
public class CurrencyConversionRequestDTO {

    private double amount;
    private String from;
    private String to;

    public CurrencyConversionRequestDTO() {
    }

    public CurrencyConversionRequestDTO(double amount, String from, String to) {
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
