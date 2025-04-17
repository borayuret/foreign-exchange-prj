package com.borayuret.fxapi.client;

public interface ExchangeRateClient {
    Double getRate(String from, String to);
}
