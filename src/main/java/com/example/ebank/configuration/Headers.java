package com.example.ebank.configuration;

public enum Headers {

    CONSUMER_ID("Consumer-Id"),
    TRACE_ID("Trace-Id"),
    LANGUAGE("Language");
    
    private String headerKey;
    
    Headers(String headerKey){
        this.headerKey = headerKey;
    }
    
    public String getHeaderKey(){
        return headerKey;
    }
    
    
}