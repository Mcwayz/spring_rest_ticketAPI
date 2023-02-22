package com.example.TicketingRestApi.ticket.restapi.model.response;

import lombok.Data;

@Data
public class PaymentStatusResponse {
    private String status;
    private String billerResponse;
    private String billerResponseCode;
}
