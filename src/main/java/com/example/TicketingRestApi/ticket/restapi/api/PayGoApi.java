package com.example.TicketingRestApi.ticket.restapi.api;

import com.example.TicketingRestApi.ticket.restapi.model.request.PaymentRequest;
import com.example.TicketingRestApi.ticket.restapi.model.request.PaymentStatusRequest;
import com.example.TicketingRestApi.ticket.restapi.model.response.PaymentResponse;
import com.example.TicketingRestApi.ticket.restapi.model.response.PaymentStatusResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PayGoApi {

  @POST("/ServiceLayer/request/postRequest")
  Call<PaymentResponse> makePayment(@Body PaymentRequest paymentRequest);

  @POST("/ServiceLayer/transaction/query")
  Call<PaymentStatusResponse> checkPaymentStatus(@Body PaymentStatusRequest paymentRequest);
  
}
