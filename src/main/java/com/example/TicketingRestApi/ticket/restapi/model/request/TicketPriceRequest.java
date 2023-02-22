package com.example.TicketingRestApi.ticket.restapi.model.request;

public class TicketPriceRequest {
    private int id;
    private int adult;
    private int children;

    public TicketPriceRequest()
    {

    }

    public TicketPriceRequest(int id, int adult, int children) {
        this.id = id;
        this.adult = adult;
        this.children = children;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TicketPriceRequest [id=" + id + ", adult=" + adult + ", children=" + children + "]";
    }

    
    
}
