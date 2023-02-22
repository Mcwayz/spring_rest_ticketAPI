package com.example.TicketingRestApi.ticket.restapi.model.request;

import lombok.Data;

@Data
public class PaymentStatusRequest {
    
    private String requestID;
    private String transactionid;
    private String clientid;
    private String username;
    private String password;
}
