package br.com.brenoborges.finflow.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Token inválido ou expirado!");
    }
}
