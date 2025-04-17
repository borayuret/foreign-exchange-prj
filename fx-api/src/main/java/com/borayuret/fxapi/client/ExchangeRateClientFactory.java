package com.borayuret.fxapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateClientFactory {

    private final FixerExchangeRateClient fixerExchangeRateClient;
    private final CurrencyLayerExchangeRateClient currencyLayerExchangeRateClient;

    @Autowired
    public ExchangeRateClientFactory(FixerExchangeRateClient fixerExchangeRateClient,
                                     CurrencyLayerExchangeRateClient currencyLayerExchangeRateClient) {
        this.fixerExchangeRateClient = fixerExchangeRateClient;
        this.currencyLayerExchangeRateClient = currencyLayerExchangeRateClient;
    }

    /**
     * Factory method to return the correct client based on the provider
     *
     * @param provider the selected API provider, 'fixer' or 'currencylayer'
     * @return the appropriate ExchangeRateClient
     */
    public ExchangeRateClient getClient(String provider) {
        if ("currencylayer".equalsIgnoreCase(provider)) {
            return currencyLayerExchangeRateClient;  // Return the Singleton instance for CurrencyLayer
        }
        return fixerExchangeRateClient;  // Return the Singleton instance for Fixer
    }
}
