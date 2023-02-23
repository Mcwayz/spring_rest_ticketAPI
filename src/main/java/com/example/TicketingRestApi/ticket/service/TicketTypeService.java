package com.example.TicketingRestApi.ticket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.TicketingRestApi.ticket.model.TicketType;
import com.example.TicketingRestApi.ticket.repository.TicketTypeRepository;

@Service
public class TicketTypeService {
    

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    //get a list of tickets 
    public List<TicketType> getTicketTypes()
    {
        return ticketTypeRepository.findAll();
    }

    //save new ticket type
    public void save(TicketType ticketType)
    {
        ticketTypeRepository.save(ticketType);
    }

    //delete by id
    public void delete(int id)
    {
        ticketTypeRepository.deleteById(id);;
    }

    //find by id
    public Optional<TicketType> getById(int id)
    {
        return ticketTypeRepository.findById(id);
    }

}
