package tobyspring.hellospring.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import tobyspring.hellospring.exrate.ExRateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.stream.Collectors;

public class WebApiExRateProvider implements ExRateProvider {
    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/"+ currency;
        // Validate the currency code
        URL uri;
        try {
            uri = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
         String response;
        try {
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                response = br.lines().collect(Collectors.joining());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExRateData data = mapper.readValue(response, ExRateData.class);
            return data.rates().get("KRW");
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse exchange rate data", e);
        }
        // Parse the JSON response


    }

}
