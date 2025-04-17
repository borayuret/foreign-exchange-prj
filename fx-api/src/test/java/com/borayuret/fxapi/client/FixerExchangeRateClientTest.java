package com.borayuret.fxapi.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
class FixerExchangeRateClientTest {

    @Autowired
    private FixerExchangeRateClient client;

    @Test
    void testSingleton() {
        // Arrange
        FixerExchangeRateClient firstInstance = client;
        FixerExchangeRateClient secondInstance = client;

        // Act & Assert
        assertSame(
                firstInstance,
                secondInstance,
                "Both autowired FixerExchangeRateClient instances should be the same (Spring singleton)"
        );
    }
}
