package com.borayuret.fxapi.dto;

import java.io.Serializable;

/**
 * Data Transfer Object representing the response structure for an exchange rate request.
 * Contains the source currency, target currency, and the calculated exchange rate.
 */
public class ExchangeRateResponseDTO implements Serializable {

    private String from;
    private String to;
    private double rate;

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor for deserialization or framework usage.
     */
    public ExchangeRateResponseDTO() {
    }

    /**
     * Constructs a response object with the given parameters.
     *
     * @param from source currency code (e.g., "USD")
     * @param to   target currency code (e.g., "EUR")
     * @param rate exchange rate from 'from' to 'to'
     */
    public ExchangeRateResponseDTO(String from, String to, double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Returns a human-readable string representation of the exchange rate.
     * Example: "1 USD = 0.8835 EUR"
     */
    @Override
    public String toString() {
        return String.format("1 %s = %.4f %s", from, rate, to);
    }
}
