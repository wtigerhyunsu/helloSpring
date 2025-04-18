package tobyspring.hellospring.payment;

import java.math.BigDecimal;

public class SimpleExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) {
        if(currency.equals("USD")) return BigDecimal.valueOf(1300);

        throw new IllegalArgumentException("Unsupported currency: " + currency);
    }
}
