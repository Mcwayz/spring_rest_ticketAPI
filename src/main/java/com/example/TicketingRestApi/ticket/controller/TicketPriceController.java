package com.example.TicketingRestApi.ticket.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.example.TicketingRestApi.ticket.restapi.model.request.TicketPriceRequest;
import com.example.TicketingRestApi.ticket.restapi.model.response.TicketPriceRest;
import com.example.TicketingRestApi.ticket.restapi.service.TicketRestService;



@RestController
public class TicketPriceController {
    
    @Autowired
    private TicketRestService ticketRestService;


    @CrossOrigin
    @PostMapping(value = "/getPrice")
    @ResponseBody
    public ModelAndView getTicketPrice(@RequestParam("tickettypeid") int tickettypeid, @RequestParam("adult") int adult, @RequestParam("children") int children) {
        TicketPriceRequest priceRequest = new TicketPriceRequest(tickettypeid, adult, children);

        Double price = ticketRestService.calculateTotalPrice(priceRequest);

        TicketPriceRest returnValue = new TicketPriceRest(priceRequest.getAdult(),priceRequest.getChildren(), price);
        double p = returnValue.getPrice();
        int a = returnValue.getAdult();
        int c = returnValue.getChildren();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ticket/ticket_buy_mno");
        modelAndView.addObject("adult", a);
        modelAndView.addObject("children", c);
        modelAndView.addObject("price", p);

      
        return modelAndView;
    }
}
