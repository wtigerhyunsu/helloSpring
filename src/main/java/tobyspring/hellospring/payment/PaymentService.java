package tobyspring.hellospring.payment;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {
    public PaymentService() {
        this.exRateProvider = new WebApiExRateProvider();
    }

    private final WebApiExRateProvider exRateProvider;

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exRate = exRateProvider.getWebExRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
    }

}
