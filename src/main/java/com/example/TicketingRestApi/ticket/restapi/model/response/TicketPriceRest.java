package com.example.TicketingRestApi.ticket.restapi.model.response;


import lombok.Data;

@Data
public class TicketPriceRest {

    private final int adult;
    private final int children;
    private final double price;

    public TicketPriceRest(int adult, int children, double price) {
        this.adult = adult;
        this.children = children;
        this.price = price;
    }

    
    
}
