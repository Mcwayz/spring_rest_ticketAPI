package com.example.TicketingRestApi.ticket.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TicketingRestApi.ticket.restapi.model.request.TicketPriceRequest;
import com.example.TicketingRestApi.ticket.restapi.model.response.TicketPriceRest;
import com.example.TicketingRestApi.ticket.restapi.service.TicketRestService;

@RestController
@RequestMapping("/api/ticketPrice")
public class TicketPriceRestController {


    @Autowired
    private TicketRestService ticketRestService;

  
    @CrossOrigin
    @PostMapping
    public TicketPriceRest getTicketPrice(@RequestBody TicketPriceRequest request) {

        Double price = ticketRestService.calculateTotalPrice(request);

        return new TicketPriceRest(request.getAdult(),request.getChildren(), price);
    }
    
}
