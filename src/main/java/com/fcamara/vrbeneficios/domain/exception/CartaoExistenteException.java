package com.fcamara.vrbeneficios.domain.exception;

public class CartaoExistenteException extends RuntimeException {
    public CartaoExistenteException(String message) {
        super(message);
    }
}
