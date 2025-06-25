package tobyspring.hellospring.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tobyspring.hellospring.api.SimpleApiExecutor;
import tobyspring.hellospring.exrate.ExRateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/"+ currency;
        // Validate the currency code
        return runApiForExRate(url);
    }

    private static BigDecimal runApiForExRate(String url) {
        URL uri;
        try {
            uri = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String response;
        try {
            response = new SimpleApiExecutor().execute(uri);
            return extractExRate(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BigDecimal extractExRate(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response, ExRateData.class);
        return data.rates().get("KRW");
    }



}
