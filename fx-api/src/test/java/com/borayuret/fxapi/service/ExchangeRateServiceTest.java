package com.borayuret.fxapi.service;

import com.borayuret.fxapi.client.ExchangeRateClient;
import com.borayuret.fxapi.client.ExchangeRateClientFactory;
import com.borayuret.fxapi.dto.ExchangeRateResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateClientFactory factory;

    @Mock
    private ExchangeRateClient mockClient;

    @InjectMocks
    private ExchangeRateService service;

    @BeforeEach
    void setUp() {
        // MockitoExtension ile @Mock ve @InjectMocks başlatılıyor
        // provider alanını reflection ile test'e uygun şekilde set ediyoruz
        ReflectionTestUtils.setField(service, "provider", "fixer");
    }

    @Test
    void whenProviderIsFixer_thenUsesFixerClient() {
        // Arrange
        String from = "USD", to = "EUR";
        double expectedRate = 0.88;
        when(factory.getClient("fixer")).thenReturn(mockClient);
        when(mockClient.getRate(from, to)).thenReturn(expectedRate);

        // Act
        ExchangeRateResponseDTO dto = service.getExchangeRate(from, to);

        // Assert
        assertEquals(from, dto.getFrom(), "Source currency should match");
        assertEquals(to, dto.getTo(), "Target currency should match");
        assertEquals(expectedRate, dto.getRate(), 1e-4, "Rate should match mocked value");
    }

    @Test
    void whenProviderIsCurrencyLayer_thenUsesCurrencyLayerClient() {
        // Arrange: provider'ı currencylayer olarak değiştir
        ReflectionTestUtils.setField(service, "provider", "currencylayer");

        String from = "GBP", to = "TRY";
        double expectedRate = 15.3;
        when(factory.getClient("currencylayer")).thenReturn(mockClient);
        when(mockClient.getRate(from, to)).thenReturn(expectedRate);

        // Act
        ExchangeRateResponseDTO dto = service.getExchangeRate(from, to);

        // Assert
        assertEquals(from, dto.getFrom(), "Source currency should match");
        assertEquals(to, dto.getTo(), "Target currency should match");
        assertEquals(expectedRate, dto.getRate(), 1e-4, "Rate should match mocked value");
    }
}
