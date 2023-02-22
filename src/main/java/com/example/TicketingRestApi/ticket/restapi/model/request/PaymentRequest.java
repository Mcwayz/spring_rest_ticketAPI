package com.example.TicketingRestApi.ticket.restapi.model.request;

import lombok.Data;

@Data
public class PaymentRequest {

    private String username;
    private String password;
    private String serviceid;
    private String clientid;
    private String amount;
    private String accountno;
    private String msisdn;
    private String currencycode;
    private int transactionid;
    private String timestamp;
    private Payload payload;


    @Data
    public static class Payload {
        private String narration;
        private String country;
    }
    
}
