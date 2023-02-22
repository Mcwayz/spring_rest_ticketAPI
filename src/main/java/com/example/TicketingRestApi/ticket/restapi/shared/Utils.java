package com.example.TicketingRestApi.ticket.restapi.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String NUMBERS = "0123456789";

    public String generateString(int length) {
        return generateRandomString(length);
    }

    public String generateIntegerString(int length) {
        return generateRandomIntegerString(length);
    }

    public int generateInteger(int length)
    {
        return generateRandomInteger(length);
    }


    //////////////////////////////////////////////////////////////////////////////////
    

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    private String generateRandomIntegerString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
        }

        return new String(returnValue);
    }

    private int generateRandomInteger(int length) {
        StringBuilder returnValue = new StringBuilder(length);
    
        for (int i = 0; i < length; i++) {
            returnValue.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
        }
    
        return Integer.parseInt(returnValue.toString());
    }


    
}
