package com.borayuret.fxapi.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeRateClientFactoryTest {

    @Autowired
    private ExchangeRateClientFactory factory;

    @Test
    void testGetFixerClient() {
        // Act
        ExchangeRateClient client = factory.getClient("fixer");

        // Assert
        assertNotNull(client, "Fixer client should not be null");
        assertTrue(client instanceof FixerExchangeRateClient, "Should return an instance of FixerExchangeRateClient");
    }

    @Test
    void testGetCurrencyLayerClient() {
        // Act
        ExchangeRateClient client = factory.getClient("currencylayer");

        // Assert
        assertNotNull(client, "CurrencyLayer client should not be null");
        assertTrue(client instanceof CurrencyLayerExchangeRateClient, "Should return an instance of CurrencyLayerExchangeRateClient");
    }
}
