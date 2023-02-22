package com.example.TicketingRestApi.ticket.restapi.service;

import java.util.List;

import com.example.TicketingRestApi.ticket.model.Ticket;
import com.example.TicketingRestApi.ticket.restapi.dto.TicketDto;
import com.example.TicketingRestApi.ticket.restapi.model.request.TicketPriceRequest;

public interface TicketRestService {
    
    //get list of tickets
    public List<Ticket> getTickets();

    //save new ticket
    // TicketDto save(TicketDto ticketDto);

    List<TicketDto> saveCash(TicketDto ticketDto);

    List<TicketDto> saveMno(TicketDto ticketDto);

    //delete ticket
    void delete(int id);

    void getById(int id);

    Ticket findByBarcode(String barcode);

    public Double calculateTotalPrice(TicketPriceRequest request);


}
