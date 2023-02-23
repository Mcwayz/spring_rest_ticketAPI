package com.example.TicketingRestApi.ticket.restapi.model.response;

import lombok.Data;

@Data
public class PaymentResponse {

    private String statusDescription;
    private String requestId;
    private String cloudPacketID;
    private String transactionID;
    private String status;
    // add getters and setters
    
}
