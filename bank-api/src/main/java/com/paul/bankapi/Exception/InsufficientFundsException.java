package com.paul.bankapi.Exception;

public class InsufficientFundsException extends Throwable {
    public InsufficientFundsException(String s) {
        super(s);
    }
}
