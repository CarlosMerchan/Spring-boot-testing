package org.accmm.test.springboot.app.Exceptions;

public class DineroInsuficienteException extends  RuntimeException{

    public DineroInsuficienteException(String message) {
        super(message);
    }
}
