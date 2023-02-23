package com.example.TicketingRestApi.ticket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TicketingRestApi.ticket.model.Ticket;
import com.example.TicketingRestApi.ticket.model.TicketType;
import com.example.TicketingRestApi.ticket.repository.TicketRepository;
import com.example.TicketingRestApi.ticket.repository.TicketTypeRepository;
import com.example.TicketingRestApi.ticket.restapi.shared.Utils;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    Utils utils;

    //get a list of tickets 
    public List<Ticket> getTickets()
    {
        return ticketRepository.findAll();
    }

    //save new ticket
    public void save(Ticket ticket)
    {
         //generate barcode from util class
         String barcode = utils.generateIntegerString(13);
         String ticketNumber = utils.generateIntegerString(8);

         Optional<TicketType> ticketType = ticketTypeRepository.findById(ticket.getTickettypeid());
         String ticketTypeName = ticketType.get().getName();

         ticket.setTicketNumber(ticketNumber);
         ticket.setBarcode(barcode);
         ticket.setTicketName(ticketTypeName);
  
        ticketRepository.save(ticket);
        
    }

    //delete by id
    public void delete(int id)
    {
        ticketRepository.deleteById(id);;
    }

    //find by id
    public Ticket getById(int id)
    {
        return ticketRepository.findById(id).orElse(null);
    }


    
}
