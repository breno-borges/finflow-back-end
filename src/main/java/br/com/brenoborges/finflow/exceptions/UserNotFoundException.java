package br.com.brenoborges.finflow.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuário não encontrado no sistema!");
    }
}
