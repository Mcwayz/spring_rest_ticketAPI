package com.example.TicketingRestApi.ticket.restapi.model.response;

import lombok.Data;

@Data
public class TicketVerificationResponse {
    
    private int id;
    private String ticketName;
    private String barcode;
    private String ticketNumber;
    private int adult;
    private int children;
    private String ticketOwner;
    private double totalPrice;

    private String status;
}
