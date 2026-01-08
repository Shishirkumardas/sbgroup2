package org.example.sbgroup2.services;

import org.example.sbgroup2.models.MasterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class BkashPaymentService {

    @Autowired
    private BkashTokenService tokenService;
    @Autowired
    private MasterDataService masterDataService;

    @Value("${bkash.base-url}")
    private String baseUrl;

    @Value("${bkash.app-key}")
    private String appKey;
    @Autowired
    private PaymentService paymentService;

    //This will not take any ID, Just amount and invoice, but for paying to each id we need to keep trac
    //So the second method makePayment is more suitable
    public Map<String, Object> createPayment(BigDecimal amount, String invoice) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tokenService.getToken());
        headers.set("X-App-Key", appKey);

        Map<String, Object> body = new HashMap<>();
        body.put("amount", amount.toString());
        body.put("currency", "BDT");
        body.put("intent", "sale");
        body.put("merchantInvoiceNumber", invoice);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                baseUrl + "/tokenized/checkout/create",
                entity,
                Map.class
        );

        return response.getBody();
    }

    //Cashback Payment //Payment for each master data //customerPayment
    public Map<String, Object> makePayment(Long masterDataId, BigDecimal amount) {

        MasterData md = masterDataService.getMasterDataById(masterDataId);

        String invoice = "MD-" + masterDataId + "-" + System.currentTimeMillis();

//        paymentService.createPendingPayment(md, amount, invoice);
        paymentService.createPendingPayment(md,amount,invoice);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(tokenService.getToken());
        headers.set("X-App-Key", appKey);

        Map<String, Object> body = Map.of(
                "amount", amount.toString(),
                "currency", "BDT",
                "intent", "sale",
                "merchantInvoiceNumber", invoice
        );

        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(
                baseUrl + "/tokenized/checkout/create",
                entity,
                Map.class
        );

        return response.getBody();
    }
}

