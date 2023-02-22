package com.example.TicketingRestApi.ticket.restapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.TicketingRestApi.ticket.model.TicketType;
import com.example.TicketingRestApi.ticket.restapi.dto.TicketTypeDto;
import com.example.TicketingRestApi.ticket.restapi.model.request.TicketTypeRequestModel;
import com.example.TicketingRestApi.ticket.restapi.model.response.TicketTypeRest;
import com.example.TicketingRestApi.ticket.restapi.service.TicketTypeRestService;

import lombok.var;

@RestController
@RequestMapping("/api/ticketTypes")
public class TicketTypeRestController {

    @Autowired
    private TicketTypeRestService ticketTypeRestService;

    @CrossOrigin
    @GetMapping
    public @ResponseBody Map<String, Object> getTicketTypes()
    {
         List<TicketType> ticketTypeList = ticketTypeRestService.getTicketTypes();

         Map<String, Object> response = new HashMap<>();
         response.put("types",  ticketTypeList);
        return response;

    }

    @CrossOrigin
    @PostMapping
    public TicketTypeRest saveTicketType(@RequestBody TicketTypeRequestModel ticketTypeDetails)
    {
       
        var ticketTypeDto = new TicketTypeDto();

        BeanUtils.copyProperties(ticketTypeDetails, ticketTypeDto);

        TicketTypeDto createdTicketType = ticketTypeRestService.save(ticketTypeDto);
        var returnValue = new TicketTypeRest();

        BeanUtils.copyProperties(createdTicketType, returnValue);
        return returnValue;
    }

    @CrossOrigin
    @PutMapping
    public String updateTicketType()
    {
        return "update ticket type was called";
    }

    @CrossOrigin
    @DeleteMapping
    public String deleteTicketType()
    {
        return "delete ticket type called";
    }
    
}
