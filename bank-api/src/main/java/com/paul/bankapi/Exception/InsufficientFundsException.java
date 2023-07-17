package com.paul.bankapi.Exception;
@SuppressWarnings("serial")
public class InsufficientFundsException extends Throwable {
    public InsufficientFundsException(String s) {
        super(s);
    }
}
