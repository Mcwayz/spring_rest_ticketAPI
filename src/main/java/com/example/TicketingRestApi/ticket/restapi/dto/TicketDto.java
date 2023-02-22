package com.example.TicketingRestApi.ticket.restapi.dto;


import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TicketDto implements Serializable{

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private static final long serialVersionUID = 42L;
    
    private int id;
    private int ticketId;
    private String ticketName;
    private String barcode;
    private String ticketNumber;

    private String email;
    private String phoneNumber;
    private int adult;
    private int children;
    private String ticketOwner;
    private int tickettypeid;
    private double totalPrice;
    private Boolean multiple;

    


    
   
}
