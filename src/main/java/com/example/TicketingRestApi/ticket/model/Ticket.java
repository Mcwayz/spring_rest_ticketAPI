package com.example.TicketingRestApi.ticket.model;

import java.io.Serializable;
import java.time.LocalDate;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ticket implements Serializable{

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="record_seq_gen")
    @SequenceGenerator(name="record_seq_gen", sequenceName="record_seq",initialValue = 8000,allocationSize = 1)
    @Column(name="id")
    private int id;

    private String email;
    private String phoneNumber;
    private int adult;
    private int children;

    @Column(unique = true)
    private String barcode;

    private String ticketNumber;
    private String ticketName;
    private String ticketOwner;
    private double totalPrice;
    @Value("${multiple:false}")
    private Boolean multiple;
    private String paymentMethod;
    private String platform;

    @DateTimeFormat(pattern = "yyyy-MM-dd")	
    @CreationTimestamp
    protected LocalDate createdDate;

    //relattionship between ticket and ticket type
    //Many tickets one ticket type 
    @ManyToOne
    @JoinColumn(name = "tickettypeid", insertable = false, updatable = false)
    private TicketType ticketType;
    private int tickettypeid;
    
}
