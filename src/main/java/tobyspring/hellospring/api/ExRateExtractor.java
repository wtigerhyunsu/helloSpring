package tobyspring.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

public interface ExRateExtractor {
    BigDecimal extractExRate(String response) throws JsonProcessingException;
}
