package com.example.TicketingRestApi.ticket.restapi.model.request;

import lombok.Data;

@Data
public class TicketRequestModel {

    private int id;
    private String email;
    private String phoneNumber;
    private int adult;
    private int children;
    private int tickettypeid;
    private Boolean multiple;

}
