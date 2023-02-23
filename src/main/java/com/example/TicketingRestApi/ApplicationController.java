package com.example.TicketingRestApi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    // home mapping
    @GetMapping("/index")
    public String home()
    {
        return "index";
    }
    
     // login mapping
     @GetMapping("/login")
     public String login()
     {
         return "/security/login";
     }

    // logot mapping
    @GetMapping("/logout")
    public String logout()
    {
        return "/security/login";
    }

    @GetMapping("/ticket")
    public String ticket()
    {
        return "/ticket/index";
    }

    @GetMapping("/report")
    public String report()
    {
        return "/report/index";
    }

    
}
