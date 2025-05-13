package com.cts.wios.exceptions;
@SuppressWarnings("serial")
public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String message) {
        super(message);
    }
}
