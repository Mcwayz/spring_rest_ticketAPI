package com.example.TicketingRestApi.ticket.restapi.service.impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.TicketingRestApi.ticket.model.Ticket;
import com.example.TicketingRestApi.ticket.model.TicketType;
import com.example.TicketingRestApi.ticket.repository.TicketRepository;
import com.example.TicketingRestApi.ticket.repository.TicketTypeRepository;
import com.example.TicketingRestApi.ticket.restapi.dto.TicketDto;
import com.example.TicketingRestApi.ticket.restapi.model.request.TicketPriceRequest;
import com.example.TicketingRestApi.ticket.restapi.service.TicketRestService;
import com.example.TicketingRestApi.ticket.restapi.shared.Utils;
import com.example.TicketingRestApi.ticket.shared.util.TicketPdf;



import lombok.var;


@Service
public class TicketRestServiceImpl implements TicketRestService {

    @Autowired
    private TicketTypeRepository ticketTypeRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    Utils utils;

    @Autowired
    TicketPdf ticketPdf;


    // @Autowired
    // PayGoApi payGoApi;

    //get ticket price by ticket type id
    @Override
    public Double calculateTotalPrice(TicketPriceRequest request) {
        // TODO Auto-generated method stub
        Optional<TicketType> ticketType = ticketTypeRepository.findById(request.getId());
        
        if (ticketType != null) {
            return request.getAdult() * ticketType.get().getPrice() + request.getChildren() * ticketType.get().getPrice() / 2;
        } else {
            throw new IllegalArgumentException("Invalid ticket type: " + request.getId());
    }
   
    }

    @Override
    public List<Ticket> getTickets() {
        // TODO Auto-generated method stub
        return ticketRepository.findAll();
    }

    
    @Override
    public List<TicketDto> saveCash(TicketDto ticketDto) {

        // TODO Auto-generated method stub
        List<TicketDto> multipleTickets = new ArrayList<>();

        if(ticketDto.getMultiple()) {
            for(int i = 0; i < ticketDto.getAdult(); i++) {
                //generate barcode and ticketNumber
                String barcode = utils.generateIntegerString(13);
                String ticketNumber = utils.generateIntegerString(8);

                var ticket = new Ticket();
                BeanUtils.copyProperties(ticketDto, ticket);

                Optional<TicketType> ticketType = ticketTypeRepository.findById(ticket.getTickettypeid());
                String ticketTypeName = ticketType.get().getName();

                ticket.setTicketNumber(ticketNumber);
                ticket.setBarcode(barcode);
                ticket.setTicketName(ticketTypeName);
                ticket.setTicketOwner("Adult");
                ticket.setPaymentMethod("Cash");
                ticket.setAdult(1);
                ticket.setChildren(0);
                Ticket storedTicketDetails = ticketRepository.save(ticket);

                var returnValue = new TicketDto();
                BeanUtils.copyProperties(storedTicketDetails, returnValue);
                //..other code
                var requestIndividual = new TicketPriceRequest(ticket.getTickettypeid(),1,0);
              
                returnValue.setTotalPrice(calculateTotalPrice(requestIndividual));
                
                //save total price to the Ticket Object
                ticket.setTotalPrice(returnValue.getTotalPrice());
              
                multipleTickets.add(returnValue);
            }
            for(int i = 0; i < ticketDto.getChildren(); i++) {
                //generate barcode and ticketNumber
                String barcode = utils.generateIntegerString(13);
                String ticketNumber = utils.generateIntegerString(8);

                var ticket = new Ticket();
                BeanUtils.copyProperties(ticketDto, ticket);

                Optional<TicketType> ticketType = ticketTypeRepository.findById(ticket.getTickettypeid());
                String ticketTypeName = ticketType.get().getName();

                ticket.setTicketNumber(ticketNumber);
                ticket.setBarcode(barcode);
                ticket.setTicketName(ticketTypeName);
                ticket.setTicketOwner("Child");
                ticket.setPaymentMethod("Cash");
                ticket.setAdult(0);
                ticket.setChildren(1);

                Ticket storedTicketDetails = ticketRepository.save(ticket);

                var returnValue = new TicketDto();
                BeanUtils.copyProperties(storedTicketDetails, returnValue);
                //..other code
                var requestIndividual = new TicketPriceRequest(ticket.getTickettypeid(),0,1);
               
                returnValue.setTotalPrice(calculateTotalPrice(requestIndividual));
              
                //save total price to the Ticket Object
                ticket.setTotalPrice(returnValue.getTotalPrice());
               
                multipleTickets.add(returnValue);
            }
        }
         else {
    
            String barcode = utils.generateIntegerString(13);
            String ticketNumber = utils.generateIntegerString(8);

            var ticket = new Ticket();
            BeanUtils.copyProperties(ticketDto, ticket);

            Optional<TicketType> ticketType = ticketTypeRepository.findById(ticket.getTickettypeid());
            String ticketTypeName = ticketType.get().getName();
            
            ticket.setTicketNumber(ticketNumber);
            ticket.setBarcode(barcode);
            ticket.setTicketName(ticketTypeName);
            ticket.setTicketOwner("");
            ticket.setPaymentMethod("Cash");

            Ticket storedTicketDetails = ticketRepository.save(ticket);

            var returnValue = new TicketDto();
            BeanUtils.copyProperties(storedTicketDetails, returnValue);

            var requestTotal = new TicketPriceRequest(ticket.getTickettypeid(),ticketDto.getAdult(),ticketDto.getChildren());

            returnValue.setTotalPrice(calculateTotalPrice(requestTotal));

            //save total price to the Ticket Object
            ticket.setTotalPrice(returnValue.getTotalPrice());
            ticketRepository.save(ticket);

            if (ticketDto.getEmail() != null && !ticketDto.getEmail().isEmpty()) {
                try {
                    ticketPdf.createPdf(returnValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            multipleTickets.add(returnValue);
           
    }

    return multipleTickets;
    
    }

   
    @Override
    public List<TicketDto> saveMno(TicketDto ticketDto) {
            // TODO Auto-generated method stub
            List<TicketDto> multipleTickets = new ArrayList<>();

            if(ticketDto.getMultiple()) {
                for(int i = 0; i < ticketDto.getAdult(); i++) {
                    //generate barcode and ticketNumber
                    String barcode = utils.generateIntegerString(13);
                    String ticketNumber = utils.generateIntegerString(8);
    
                    var ticket = new Ticket();
                    BeanUtils.copyProperties(ticketDto, ticket);
    
                    Optional<TicketType> ticketType = ticketTypeRepository.findById(ticket.getTickettypeid());
                    String ticketTypeName = ticketType.get().getName();
    
                    ticket.setTicketNumber(ticketNumber);
                    ticket.setBarcode(barcode);
                    ticket.setTicketName(ticketTypeName);
                    ticket.setTicketOwner("Adult");
                    ticket.setPaymentMethod("MNO");
                    ticket.setAdult(1);
                    ticket.setChildren(0);
                    Ticket storedTicketDetails = ticketRepository.save(ticket);
    
                    var returnValue = new TicketDto();
                    BeanUtils.copyProperties(storedTicketDetails, returnValue);
                    //..other code
                    var requestIndividual = new TicketPriceRequest(ticket.getTickettypeid(),1,0);
                    // var requestTotal = new TicketPriceRequest(ticket.getTickettypeid(),ticketDto.getAdult(),ticketDto.getChildren());
    
                    returnValue.setTotalPrice(calculateTotalPrice(requestIndividual));
                    
                    //save total price to the Ticket Object
                    ticket.setTotalPrice(returnValue.getTotalPrice());
                  
                    multipleTickets.add(returnValue);
                }
                for(int i = 0; i < ticketDto.getChildren(); i++) {
                    //generate barcode and ticketNumber
                    String barcode = utils.generateIntegerString(13);
                    String ticketNumber = utils.generateIntegerString(8);
    
                    var ticket = new Ticket();
                    BeanUtils.copyProperties(ticketDto, ticket);
    
                    Optional<TicketType> ticketType = ticketTypeRepository.findById(ticket.getTickettypeid());
                    String ticketTypeName = ticketType.get().getName();
    
                    ticket.setTicketNumber(ticketNumber);
                    ticket.setBarcode(barcode);
                    ticket.setTicketName(ticketTypeName);
                    ticket.setTicketOwner("Child");
                    ticket.setPaymentMethod("MNO");
                    ticket.setAdult(0);
                    ticket.setChildren(1);
    
                    Ticket storedTicketDetails = ticketRepository.save(ticket);
    
                    var returnValue = new TicketDto();
                    BeanUtils.copyProperties(storedTicketDetails, returnValue);
                    //..other code
                    var requestIndividual = new TicketPriceRequest(ticket.getTickettypeid(),0,1);
                
                    returnValue.setTotalPrice(calculateTotalPrice(requestIndividual));
                
                    //save total price to the Ticket Object
                    ticket.setTotalPrice(returnValue.getTotalPrice());
                
                    multipleTickets.add(returnValue);
                }
            }
             else {
        
                    
                String barcode = utils.generateIntegerString(13);
                String ticketNumber = utils.generateIntegerString(8);

                var ticket = new Ticket();
                BeanUtils.copyProperties(ticketDto, ticket);

                Optional<TicketType> ticketType = ticketTypeRepository.findById(ticket.getTickettypeid());
                String ticketTypeName = ticketType.get().getName();
                
                ticket.setTicketNumber(ticketNumber);
                ticket.setBarcode(barcode);
                ticket.setTicketName(ticketTypeName);
                ticket.setTicketOwner("");
                ticket.setPaymentMethod("MNO");

                Ticket storedTicketDetails = ticketRepository.save(ticket);

                var returnValue = new TicketDto();
                BeanUtils.copyProperties(storedTicketDetails, returnValue);

                var requestTotal = new TicketPriceRequest(ticket.getTickettypeid(),ticketDto.getAdult(),ticketDto.getChildren());

                returnValue.setTotalPrice(calculateTotalPrice(requestTotal));

                //save total price to the Ticket Object
                ticket.setTotalPrice(returnValue.getTotalPrice());
                ticketRepository.save(ticket);

                if (ticketDto.getEmail() != null && !ticketDto.getEmail().isEmpty()) {
                    try {
                        ticketPdf.createPdf(returnValue);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                multipleTickets.add(returnValue);
               
        }
    
    
        return multipleTickets;
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void getById(int id) {
        // TODO Auto-generated method stub
        
    }
    //find by barcode 
    @Override
    public Ticket findByBarcode(String barcode) {
        // TODO Auto-generated method stub
        
        return ticketRepository.findByBarcode(barcode);
        
    }

}
