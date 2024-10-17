package br.com.brenoborges.finflow.exceptions;

public record ErrorMessageDTO(
        String message,
        String field) {
}
