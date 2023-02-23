package com.example.TicketingRestApi.reports.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TicketingRestApi.ticket.repository.TicketRepository;

@Service
public class ReportService {

    @Autowired
    private TicketRepository ticketRepository;

        //count all tickets
    //working
    public Long countAllTickets() {
      return ticketRepository.count();
    }
  
  //working
  public Double getTotalPriceSum() {
      return ticketRepository.getTotalPriceSum();
  }
      
  
  //
//   public List<Object[]> getSumOfSinglePriceForCashPaymentsByYear() {
//       return ticketRepository.getSumOfSinglePriceForCashPaymentsByYear();
//   }
//   public List<Object[]> getSumOfSinglePriceForMNOPaymentsByYear() {
//       return ticketRepository.getSumOfSinglePriceForMNOPaymentsByYear();
//   }

  //get count of ticket types
  //working 
  public List<Object[]> countAllByTicketTypeAndName() {
      return ticketRepository. countAllByTicketTypeAndName();
    }

    public List<Object[]> getSumOfTotalPriceForCashPaymentsByDayOfWeek(){
        List<Object[]> cashPayments = ticketRepository.getSumOfTotalPriceForCashPaymentsByDayOfWeek();
        List<Object[]> formattedCashPaymentsByDayOfWeek = new ArrayList<>();

        for (Object[] cashPayment : cashPayments) {
            int day = ((Number) cashPayment[0]).intValue();
            int count = ((Number) cashPayment[1]).intValue();
            String dayOfWeek;
  
            switch (day) {
                case 1:
                    dayOfWeek = "Sun";
                    break;
                case 2:
                    dayOfWeek = "Mon";
                    break;
                case 3:
                    dayOfWeek = "Tue";
                    break;
                case 4:
                    dayOfWeek = "Wed";
                    break;
                case 5:
                    dayOfWeek = "Thur";
                    break;
                case 6:
                    dayOfWeek = "Fri";
                    break;
                case 7:
                    dayOfWeek = "Sat";
                    break;
                default:
                    dayOfWeek = "Invalid day";
            }
  
            formattedCashPaymentsByDayOfWeek.add(new Object[] {dayOfWeek, count});
        }
  
        return formattedCashPaymentsByDayOfWeek;

    }

    public List<Object[]> getSumOfTotalPriceForMNOPaymentsByDayOfWeek(){
        List<Object[]> mnoPayments = ticketRepository.getSumOfTotalPriceForMNOPaymentsByDayOfWeek();
        List<Object[]> formattedMNOPaymentsByDayOfWeek = new ArrayList<>();

        for (Object[] mnoPayment : mnoPayments) {
            int day = ((Number) mnoPayment[0]).intValue();
            int count = ((Number) mnoPayment[1]).intValue();
            String dayOfWeek;
  
            switch (day) {
                case 1:
                    dayOfWeek = "Sun";
                    break;
                case 2:
                    dayOfWeek = "Mon";
                    break;
                case 3:
                    dayOfWeek = "Tue";
                    break;
                case 4:
                    dayOfWeek = "Wed";
                    break;
                case 5:
                    dayOfWeek = "Thur";
                    break;
                case 6:
                    dayOfWeek = "Fri";
                    break;
                case 7:
                    dayOfWeek = "Sat";
                    break;
                default:
                    dayOfWeek = "Invalid day";
            }
  
            formattedMNOPaymentsByDayOfWeek.add(new Object[] {dayOfWeek, count});
        }
  
        return formattedMNOPaymentsByDayOfWeek;
    }

    public List<Object[]> countTicketsByDayOfWeek() {
      List<Object[]> ticketCounts = ticketRepository.countTicketsByDayOfWeek();
      List<Object[]> formattedTicketCounts = new ArrayList<>();

      for (Object[] ticketCount : ticketCounts) {
          int day = ((Number) ticketCount[0]).intValue();
          int count = ((Number) ticketCount[1]).intValue();
          String dayOfWeek;

          switch (day) {
              case 1:
                  dayOfWeek = "Sun";
                  break;
              case 2:
                  dayOfWeek = "Mon";
                  break;
              case 3:
                  dayOfWeek = "Tue";
                  break;
              case 4:
                  dayOfWeek = "Wed";
                  break;
              case 5:
                  dayOfWeek = "Thur";
                  break;
              case 6:
                  dayOfWeek = "Fri";
                  break;
              case 7:
                  dayOfWeek = "Sat";
                  break;
              default:
                  dayOfWeek = "Invalid day";
          }

          formattedTicketCounts.add(new Object[] {dayOfWeek, count});
      }

      return formattedTicketCounts;
  }

  public List<Object[]> getTicketCountsForPreviousWeek() {
      LocalDate today = LocalDate.now();
      LocalDate startDate = today.minusWeeks(1).with(DayOfWeek.MONDAY);
      LocalDate endDate = startDate.plusDays(6);
      List<Object[]> ticketCounts = ticketRepository.countTicketsForPreviousWeek(startDate, endDate);

      return ticketCounts;
  }

    
}
