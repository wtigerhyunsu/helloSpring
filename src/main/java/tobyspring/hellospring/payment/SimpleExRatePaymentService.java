package tobyspring.hellospring.payment;

import java.math.BigDecimal;

public class SimpleExRatePaymentService extends PaymentService {
    @Override
    BigDecimal getExRate(String currency) {
        if(currency.equals("USD")) return BigDecimal.valueOf(1300);

        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
