package com.tallerwebi.dominio.excepcion;

public class WebsocketError extends RuntimeException {
    public WebsocketError(String message) {
        super("Websocket operation error: " + message);
    }
}
