package com.cts.wios.exceptions;
@SuppressWarnings("serial")
public class VendorNotFoundException extends RuntimeException {
    public VendorNotFoundException(String message) {
        super(message);
    }
}
