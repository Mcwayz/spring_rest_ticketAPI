package com.example.TicketingRestApi.ticket.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.TicketingRestApi.ticket.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    //find by barcode 
    Ticket findByBarcode(String barcode);

    //get total of single tickets 
 
    @Query("SELECT SUM(t.totalPrice) FROM Ticket t")
    Double getTotalPriceSum();

    //get the sum of total price for cash payments
    // @Query(value = "SELECT year(created_date) as year, sum(total_price) as sum_of_total_price " +
    // "FROM ticket " +
    // "WHERE payment_method = 'Cash' " +
    // "GROUP BY year(created_date)", nativeQuery = true)
    // List<Object[]> getSumOfSinglePriceForCashPaymentsByYear();

    @Query("SELECT dayOfWeek(t.createdDate) as day, sum(t.totalPrice) as sum_of_total_price " +
    "FROM Ticket t " +
    "WHERE t.paymentMethod = 'Cash' " +
    "GROUP BY dayOfWeek(t.createdDate)")
    List<Object[]> getSumOfTotalPriceForCashPaymentsByDayOfWeek();

    @Query(value = "SELECT dayOfWeek(created_date) as day, sum(total_price) as sum_of_total_price " +
    "FROM ticket " +
    "WHERE payment_method = 'MNO' " +
    "GROUP BY dayOfWeek(created_date)", nativeQuery = true)
    List<Object[]> getSumOfTotalPriceForMNOPaymentsByDayOfWeek();


    //get the sum of total price for mno payments
    // @Query(value = "SELECT year(created_date) as year, sum(total_price) as sum_of_total_price " +
    // "FROM ticket " +
    // "WHERE payment_method = 'MNO' " +
    // "GROUP BY year(created_date)", nativeQuery = true)
    // List<Object[]> getSumOfSinglePriceForMNOPaymentsByYear();

    //count tickets by ticket type
    @Query("SELECT t.tickettypeid, t.ticketName, COUNT(t) FROM Ticket t GROUP BY t.tickettypeid")
    List<Object[]> countAllByTicketTypeAndName();

    //get the sum of children and adult 
    @Query(value = "SELECT ticketName, SUM(adult), SUM(children), SUM(totalPrice) FROM Ticket WHERE createdDate = :createdDate GROUP BY ticketName")
    List<Object[]> findByTicketNameAndCreatedDate(@Param("createdDate") LocalDate createdDate);
    //
    @Query("SELECT t.ticketName, sum(t.adult), sum(t.children), sum(t.totalPrice) FROM Ticket t WHERE t.createdDate >= :startDate AND t.createdDate <= :endDate GROUP BY t.ticketName")
    List<Object[]> findTotalAdultChildrenAndTotalPriceByTicketNameAndDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //query to count the number of tickets for each day of the week
    @Query("SELECT dayOfWeek(t.createdDate) as day, count(t.id) as count FROM Ticket t WHERE dayOfWeek(t.createdDate) IN (1,2,3,4,5,6,7) GROUP BY dayOfWeek(t.createdDate)")
    List<Object[]> countTicketsByDayOfWeek();
    //
    @Query("SELECT dayOfWeek(t.createdDate) as day, count(t.id) as count FROM Ticket t WHERE t.createdDate >= :startDate AND t.createdDate < :endDate AND dayOfWeek(t.createdDate) IN (1,2,3,4,5,6,7) GROUP BY dayOfWeek(t.createdDate)")
    List<Object[]> countTicketsForPreviousWeek(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //get percentage of cash payments
    @Query("SELECT COUNT(t) * 100.0 / (SELECT COUNT(tt) FROM Ticket tt) FROM Ticket t WHERE t.paymentMethod = 'Cash'")
    Double getCashPaymentPercentage();
     //get percentage of mno payments
    // @Query("SELECT COUNT(t) * 100.0 / (SELECT COUNT(tt) FROM Ticket tt) FROM Ticket t WHERE t.paymentMethod = 'MNO'")
    // Double getMNOPaymentPercentage();


    
}
