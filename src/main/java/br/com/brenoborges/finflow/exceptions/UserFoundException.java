package br.com.brenoborges.finflow.exceptions;

public class UserFoundException extends RuntimeException {

    public UserFoundException() {
        super("E-mail já cadastrado no sistema!");
    }
}
