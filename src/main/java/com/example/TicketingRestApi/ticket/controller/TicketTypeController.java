package com.example.TicketingRestApi.ticket.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.TicketingRestApi.ticket.model.TicketType;
import com.example.TicketingRestApi.ticket.service.TicketTypeService;

@Controller
public class TicketTypeController {
    
    @Autowired
    TicketTypeService ticketTypeService;


    //get list of property types
    //working
    @GetMapping("/ticket/ticketTypes")
     public String getTicketTypes(Model model)
     {
        List<TicketType> ticketTypeList = ticketTypeService.getTicketTypes();
        model.addAttribute("ticketTypes", ticketTypeList);
         return "ticket/ticket_type";
     }

     //find by id
     //working
     @RequestMapping("/ticket/ticketType/{id}")
     @ResponseBody
     public Optional<TicketType> findById(@PathVariable Integer id)
     {
        return ticketTypeService.getById(id);
     }

     //save new ticket type
     //working
     @PostMapping(value = "/ticket/ticketTypes")
     public String save(TicketType ticketType)
     {
        ticketTypeService.save(ticketType);
        return "redirect:/ticket/ticketTypes";
     }

     //delete ticket type by id
     //working
     @RequestMapping(value="/ticket/ticketType/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
     public String delete(@PathVariable Integer id) {
         ticketTypeService.delete(id);
         return "redirect:/ticket/ticketTypes";
     }
}
