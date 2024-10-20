package com.fcamara.vrbeneficios.adapter.exception.handler;

import com.fcamara.vrbeneficios.adapter.exception.handler.response.ApiErroResponse;
import com.fcamara.vrbeneficios.domain.exception.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerApi {

    @ExceptionHandler(CartaoExistenteException.class)
    public ResponseEntity<Object> handlerExceptionCartaoExistente(CartaoExistenteException cartaoExistenteException) {
        var erroResponse = new ApiErroResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Não foi possivel criar o cartao");
        return new ResponseEntity<>(erroResponse, erroResponse.getHttpStatus());
    }

    @ExceptionHandler(SaldoCartaoInvalidoException.class)
    public ResponseEntity<Object> handlerExceptionSaldoCartaoInvalido(SaldoCartaoInvalidoException saldoCartaoInvalidoException) {
        var erroResponse = new ApiErroResponse(HttpStatus.BAD_REQUEST, saldoCartaoInvalidoException.getMessage());
        return new ResponseEntity<>(erroResponse, erroResponse.getHttpStatus());
    }

    @ExceptionHandler({SaldoInsuficienteException.class, SenhaInvalidaException.class, CartaoNaoEncontradoException.class})
    public ResponseEntity<Object> handlerExceptionTransacao(RuntimeException runtimeException) {
        if (runtimeException.getMessage().isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        var erroResponse = new ApiErroResponse(HttpStatus.UNPROCESSABLE_ENTITY, runtimeException.getMessage());
        return new ResponseEntity<>(erroResponse, erroResponse.getHttpStatus());
    }
}
