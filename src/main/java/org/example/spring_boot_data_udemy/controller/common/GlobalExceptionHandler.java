package org.example.spring_boot_data_udemy.controller.common;

import org.example.spring_boot_data_udemy.controller.dto.ErroCampo;
import org.example.spring_boot_data_udemy.controller.dto.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice // Classe para tratar exceptions e retornar response
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // Define o status a ser retornado pela request quando nao estamos enviando uma ResponseEntity
    public ErroResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .toList();

        return new ErroResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validacao",
                listaErros);
    }
}
