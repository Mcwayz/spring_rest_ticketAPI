package com.example.TicketingRestApi.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String description;
    private String details;
}
