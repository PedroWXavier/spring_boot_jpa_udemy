package org.example.spring_boot_data_udemy.exceptions;

public class RegistroDuplicadoException extends RuntimeException {
    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
