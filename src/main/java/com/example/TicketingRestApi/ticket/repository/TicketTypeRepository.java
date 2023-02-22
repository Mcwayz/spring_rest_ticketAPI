package com.example.TicketingRestApi.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.TicketingRestApi.ticket.model.TicketType;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Integer> {
    //find by ticket name
    TicketType findByName(String name);
}
