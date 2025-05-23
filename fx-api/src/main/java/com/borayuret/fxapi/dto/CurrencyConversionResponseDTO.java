package com.borayuret.fxapi.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * Response DTO for currency conversion.
 * Contains the converted amount and the unique transaction ID.
 */
public class CurrencyConversionResponseDTO implements Serializable {

    private double convertedAmount;
    private UUID transactionId;

    private static final long serialVersionUID = 1L;

    public CurrencyConversionResponseDTO() {
    }

    public CurrencyConversionResponseDTO(double convertedAmount, UUID transactionId) {
        this.convertedAmount = convertedAmount;
        this.transactionId = transactionId;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
