package com.borayuret.fxapi.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CurrencyLayerExchangeRateClientTest {

    @Autowired
    private CurrencyLayerExchangeRateClient client;

    @Test
    void testSingleton() {
        // Arrange
        CurrencyLayerExchangeRateClient firstInstance = client;
        CurrencyLayerExchangeRateClient secondInstance = client;

        // Act & Assert
        assertSame(firstInstance, secondInstance, "Both instances should be the same (Singleton)");
    }
}
