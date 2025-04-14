package tobyspring.hellospring.payment;



import com.fasterxml.jackson.databind.ObjectMapper;
import tobyspring.hellospring.exrate.ExRateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PaymentService {
    // 현제 코드에는 관심사가 기술적인 부분 & 비즈니스 로직이 섞여있다.
    // 관심사는 변경에 중심적으로 생각해야한다 java HttpURLConnectio을 용하지 말고 최신 기술을 사용하자(기술적인 부분의 변경)
    // 환율을 가져오는 방법을 다른 걸로 변경 하자(매커니즘이 변경), 외환의 금액을 어떻게 한화로 변경할지 또는 언제까지 유효한지(비즈니스 로직의 변경)
    // 이런 관심사들이 포함 되어 있다. 이를 위해서 관심사를 분리해야한다. 변경의 이유와 변경의 시점이 다른 부분에 대해서 분리해야 한다.
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        //환율 가져오기
        URL url = new URL("https://open.er-api.com/v6/latest/"+currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExRateData data = mapper.readValue(response,ExRateData.class);

        BigDecimal exRate = data.rates().get("KRW");
        //금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        //유효 시간 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount, exRate,
                convertedAmount, validUntil);
    }

    public static void main(String[] args) throws IOException {
        PaymentService paymentService =  new PaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.2));
        System.out.println(payment);
    }
}
