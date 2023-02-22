package com.example.TicketingRestApi.ticket.restapi.service;

import java.util.List;

import com.example.TicketingRestApi.ticket.model.TicketType;
import com.example.TicketingRestApi.ticket.restapi.dto.TicketTypeDto;

public interface TicketTypeRestService {
    //get list of ticket types
    public List<TicketType> getTicketTypes();
    //save new ticket
    TicketTypeDto save(TicketTypeDto ticketTypeDto);
    // delete by id
    void delete(int id);
    //get by id
    TicketType getById(int id);

}
