package com.example.TicketingRestApi.reports.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.TicketingRestApi.reports.service.ReportService;
import com.example.TicketingRestApi.ticket.repository.TicketRepository;


@Controller
public class DashboardController {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ReportService reportService;

    
     //dashbord data
     @GetMapping("/")
     public String home(Model model) {
       // Retrieve the total sum of all ticket prices from the report service.
       Double totalRevenue = reportService.getTotalPriceSum();
     
       // Count the total number of tickets in the database using the report service.
       Long count = reportService.countAllTickets();
     
       // Retrieve the total sum of ticket prices for cash payments grouped by day of the week.
       List<Object[]> resultCash = reportService.getSumOfTotalPriceForCashPaymentsByDayOfWeek();
     
       // Retrieve the total sum of ticket prices for MNO payments grouped by day of the week.
       List<Object[]> resultMNO = reportService.getSumOfTotalPriceForMNOPaymentsByDayOfWeek();
     
       // Count the number of tickets for each ticket type and name using the report service.
       List<Object[]> ticketTypeCount = reportService.countAllByTicketTypeAndName();
     
       // Count the number of tickets sold for each day of the week using the report service.
       List<Object[]> ticketByDayOfWeekPresent = reportService.countTicketsByDayOfWeek();
     
       // Retrieve the number of tickets sold for each day of the week for the previous week using the report service.
       List<Object[]> ticketByDayOfWeekLast = reportService.getTicketCountsForPreviousWeek();
     
       // Calculate the percentage of cash payments and round to the nearest integer.
       Double cashPaymentPercentage = ticketRepository.getCashPaymentPercentage();
       if (cashPaymentPercentage != null) {
           int roundedCashPercentage = (int) Math.round(cashPaymentPercentage);
           int roundedMnoPercentage = 100 - roundedCashPercentage;
           model.addAttribute("cashPercentage", roundedCashPercentage);
           model.addAttribute("mnoPercentage", roundedMnoPercentage);
       } else {
           // handle the case where cashPaymentPercentage is null
           model.addAttribute("cashPercentage", 0);
           model.addAttribute("mnoPercentage", 0);
       }
       
     
       // Add all the data to the model object that will be used to render the view.
       model.addAttribute("ticketTypeCount", ticketTypeCount);
       model.addAttribute("resultCash",resultCash);
       model.addAttribute("resultMNO",resultMNO);
       model.addAttribute("totalRevenue", totalRevenue);
       model.addAttribute("count", count);
       model.addAttribute("tickets", ticketRepository.findAll());
       model.addAttribute("ticketByDay", ticketByDayOfWeekPresent);
       model.addAttribute("ticketByDayLast", ticketByDayOfWeekLast);
       // Return the name of the view template to be used to render the response.
       return "index";
     }
     

}
