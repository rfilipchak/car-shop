package com.playtika.carshop.exeptions;


public class DealNotFoundExeption extends RuntimeException{
    private final long requestedId;

    public DealNotFoundExeption(String message, long requestedId) {
        super(message);
        this.requestedId = requestedId;
    }
    public long getRequestedId() {
        return requestedId;
    }
}
