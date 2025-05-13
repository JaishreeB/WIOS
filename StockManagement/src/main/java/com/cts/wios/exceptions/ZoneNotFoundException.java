package com.cts.wios.exceptions;
@SuppressWarnings("serial")
public class ZoneNotFoundException extends RuntimeException {
    public ZoneNotFoundException(String message) {
        super(message);
    }
}
