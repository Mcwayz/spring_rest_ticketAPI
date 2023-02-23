package com.example.TicketingRestApi.ticket.restapi.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TicketingRestApi.ticket.model.TicketType;
import com.example.TicketingRestApi.ticket.repository.TicketTypeRepository;
import com.example.TicketingRestApi.ticket.restapi.dto.TicketTypeDto;
import com.example.TicketingRestApi.ticket.restapi.service.TicketTypeRestService;

import lombok.var;

@Service
public class TicketTypeRestServiceImpl implements TicketTypeRestService {

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Override
    public List<TicketType> getTicketTypes() {
        // TODO Auto-generated method stub
        return ticketTypeRepository.findAll();
    }

    @Override
    public TicketTypeDto save(TicketTypeDto ticketTypeDto) {
        // TODO Auto-generated method stub
        
        //expecption handling by checking ticket name 
        if(ticketTypeRepository.findByName(ticketTypeDto.getName()) != null) throw new RuntimeException("Record already exists");
        
        var ticketType = new TicketType();
        BeanUtils.copyProperties(ticketTypeDto, ticketType);
        
        TicketType storedTicketTypeDetails = ticketTypeRepository.save(ticketType);

        var returnValue = new TicketTypeDto();
        BeanUtils.copyProperties(storedTicketTypeDetails, returnValue);
        return returnValue;
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public TicketType getById(int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
