package com.example.TicketingRestApi.ticket.restapi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

import com.example.TicketingRestApi.ticket.model.Ticket;
import com.example.TicketingRestApi.ticket.restapi.dto.TicketDto;
import com.example.TicketingRestApi.ticket.restapi.model.request.PaymentRequest;
import com.example.TicketingRestApi.ticket.restapi.model.request.PaymentStatusRequest;
import com.example.TicketingRestApi.ticket.restapi.model.request.TicketPriceRequest;
import com.example.TicketingRestApi.ticket.restapi.model.request.TicketRequestModel;
import com.example.TicketingRestApi.ticket.restapi.model.request.TicketVerificationRequest;
import com.example.TicketingRestApi.ticket.restapi.model.response.PaymentResponse;
import com.example.TicketingRestApi.ticket.restapi.model.response.PaymentStatusResponse;
import com.example.TicketingRestApi.ticket.restapi.model.response.TicketRest;
import com.example.TicketingRestApi.ticket.restapi.model.response.TicketVerificationResponse;
import com.example.TicketingRestApi.ticket.restapi.service.PayGoApiService;
import com.example.TicketingRestApi.ticket.restapi.service.TicketRestService;
import com.example.TicketingRestApi.ticket.restapi.shared.Utils;

import lombok.var;
import retrofit2.Call;
import retrofit2.Response;

@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    private TicketRestService ticketRestService;

     @Autowired
     Utils utils;

    private String username = "Izyane";
    private String password ="520ca8c7d7a2323c4d4cc4202e7bd1f848b35f16e0575d5678c5a8c9abe1ba4e4185c5cb8160de3a851dc1432f84435e97fb9807a47f07328db407e567b131e4";
    private String clientId = "31";

    private String saveCurrentDate, saveCurrentTime;
    private String datestamp;
        
    @CrossOrigin
    @GetMapping
    public @ResponseBody  Map<String, Object> getAllTickets() {
        List<Ticket>  getTicketList = ticketRestService.getTickets();
        

        Map<String, Object> response = new HashMap<>();
        response.put("ticket",  getTicketList);
        return response;
    }


    //buy new ticket cash 
    @CrossOrigin
    @PostMapping
    public @ResponseBody Map<String, List<TicketRest>> saveTicketCash(@RequestBody TicketRequestModel ticketDetails) {
        var ticketDto = new TicketDto();
        BeanUtils.copyProperties(ticketDetails, ticketDto);
    
        List<TicketDto> createdTickets = ticketRestService.saveCash(ticketDto);
        List<TicketRest> multipleTickets = new ArrayList<>();
        for(TicketDto createdTicket : createdTickets) {
            var returnValue = new TicketRest();
            BeanUtils.copyProperties(createdTicket, returnValue);
            multipleTickets.add(returnValue);
        }
        Map<String, List<TicketRest>> response = new HashMap<>();
        response.put("ticket", multipleTickets);
        return response;
    }

     //buy new ticket mno 
     
     @CrossOrigin
     @PostMapping("/mno")// maps this endpoint to a POST request with a URL of "/mno".
     public @ResponseBody Map<String, List<Object>> saveTicketAirtel(@RequestBody TicketRequestModel ticketDetails) throws InterruptedException, ExecutionException {
        var ticketDto = new TicketDto();
        BeanUtils.copyProperties(ticketDetails, ticketDto);

        TicketPriceRequest priceRequest = new TicketPriceRequest();
        priceRequest.setId(ticketDetails.getTickettypeid());
        priceRequest.setAdult(ticketDetails.getAdult());
        priceRequest.setChildren(ticketDetails.getChildren());
        
        Double totalPrice = ticketRestService.calculateTotalPrice(priceRequest);

        int ticketPrice = totalPrice.intValue();
        int txnId = utils.generateInteger(8);

        // Save current date and time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        datestamp = saveCurrentDate + saveCurrentTime;

        String phoneNumber = ticketDto.getPhoneNumber();
        String prefix = phoneNumber.substring(0, 3);
        String modifiedPhoneNumber = phoneNumber.substring(1);

        // Set the service ID based on the phone number prefix
        String serviceId = "7"; // default service ID
        if (prefix.equals("095")) {
            serviceId = "5";
        } else if (prefix.equals("097")) {
            serviceId = "7";
        } else if (prefix.equals("096")) {
            serviceId = "1";
        }

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUsername(username);
        paymentRequest.setPassword(password);
        paymentRequest.setServiceid(serviceId);
        paymentRequest.setClientid(clientId);
        paymentRequest.setAmount(String.valueOf(ticketPrice));
        paymentRequest.setAccountno(modifiedPhoneNumber);
        paymentRequest.setMsisdn(modifiedPhoneNumber);
        paymentRequest.setCurrencycode("ZMW");
        paymentRequest.setTransactionid(txnId);
        paymentRequest.setTimestamp(datestamp);
        PaymentRequest.Payload payload = new PaymentRequest.Payload();
        payload.setNarration("Make payment");
        payload.setCountry("ZM");
        paymentRequest.setPayload(payload);  
        
        Call<PaymentResponse> paymentCall = PayGoApiService.getPaymentApiService().makePayment(paymentRequest);

        Map<String, List<Object>> responseMap = new HashMap<>();
        try {
            Response<PaymentResponse> paymentResponse = paymentCall.execute();
            if (paymentResponse.isSuccessful() && paymentResponse.body().getStatus().equals("00")) {
                String requestId = paymentResponse.body().getRequestId();
                String txnIdR = paymentResponse.body().getTransactionID();
                PaymentStatusRequest paymentStatusRequest = new PaymentStatusRequest();
                paymentStatusRequest.setRequestID(requestId);
                paymentStatusRequest.setTransactionid(txnIdR);
                paymentStatusRequest.setClientid(clientId);
                paymentStatusRequest.setUsername(username);
                paymentStatusRequest.setPassword(password);

                Call<PaymentStatusResponse> call = PayGoApiService.getPaymentApiService().checkPaymentStatus(paymentStatusRequest);
                long endTime = System.currentTimeMillis() + 60 * 1000; // 60 seconds in milliseconds
                while (System.currentTimeMillis() < endTime) {
                    Response<PaymentStatusResponse> paymentStatusResponse = call.clone().execute();
                    if (paymentStatusResponse.isSuccessful() && paymentStatusResponse.body().getStatus().equals("00")) {
                        break;
                    } else if (paymentStatusResponse.isSuccessful() && paymentStatusResponse.body().getStatus().equals("55")) {
                        PaymentStatusResponse paymentStatus = paymentStatusResponse.body();
                        responseMap.put("billerResponse", Collections.singletonList(paymentStatus.getBillerResponse()));
                        responseMap.put("billerResponseCode", Collections.singletonList(paymentStatus.getBillerResponseCode()));
                        break;
                    }
                    Thread.sleep(1000); // wait for 1 second
                }
                Response<PaymentStatusResponse> paymentStatusResponse = call.execute();
                if (paymentStatusResponse.isSuccessful()) {
                    if (paymentStatusResponse.body().getStatus().equals("00")) {
                        List<TicketDto> createdTickets = ticketRestService.saveMno(ticketDto);
                        List<Object> multipleTickets = new ArrayList<>();
                        for (TicketDto createdTicket : createdTickets) {
                            TicketRest returnValue = new TicketRest();
                            BeanUtils.copyProperties(createdTicket, returnValue);
                            multipleTickets.add(returnValue);
                        }
                        responseMap.put("ticket", multipleTickets);
                    } else {
                        PaymentStatusResponse paymentStatus = paymentStatusResponse.body();
                        String billerResponse = paymentStatus.getBillerResponse();
                        if (billerResponse == null || billerResponse.isEmpty()) {
                            responseMap.put("billerResponse", Collections.singletonList("Transaction Failed"));
                            responseMap.put("billerResponseCode", Collections.singletonList("55"));
                        } else {
                            responseMap.put("billerResponse", Collections.singletonList(billerResponse));
                            responseMap.put("billerResponseCode", Collections.singletonList(paymentStatus.getBillerResponseCode()));
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return responseMap;

     }
     
    @CrossOrigin
    @PutMapping
    public String updateTicket()
    {
        return "update ticket was called";
    }

    @CrossOrigin
    @DeleteMapping
    public String deleteTicket()
    {
        return "delete ticket called";
    }

    @PostMapping("/verify")
    public @ResponseBody Map<String, List<TicketVerificationResponse>> verifyTicket(@RequestBody TicketVerificationRequest request) {
    
        Ticket ticket = ticketRestService.findByBarcode(request.getBarcode());
    
        TicketVerificationResponse response = new TicketVerificationResponse();
        if (ticket != null) {
            response.setId(ticket.getId());
            response.setTicketName(ticket.getTicketName());
            response.setBarcode(ticket.getBarcode());
            response.setTicketNumber(ticket.getTicketNumber());
            response.setAdult(ticket.getAdult());
            response.setChildren(ticket.getChildren());
            response.setTicketOwner(ticket.getTicketOwner());
            response.setTotalPrice(ticket.getTotalPrice());
            response.setStatus("VALID");
        } else {
            response.setId(0);
            response.setTicketName("");
            response.setBarcode("");
            response.setTicketNumber("");
            response.setAdult(0);
            response.setChildren(0);
            response.setTicketOwner("");
            response.setTotalPrice(0);
            response.setStatus("INVALID");
        }
        Map<String, List<TicketVerificationResponse>> responseMap = new HashMap<>();
        List<TicketVerificationResponse> responseList = new ArrayList<>();
        responseList.add(response);
        responseMap.put("ticket", responseList);
        return responseMap;
    }
    
    
}
