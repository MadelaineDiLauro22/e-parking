package com.tallerwebi.dominio.excepcion;

public class CantSendMessageException extends RuntimeException {

    public CantSendMessageException() {
        super("Can't send message to websocket session");
    }

    public CantSendMessageException(String message) {
        super("Can't send message to websocket session - Exception: " + message);
    }
}
