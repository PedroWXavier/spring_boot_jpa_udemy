package org.example.spring_boot_data_udemy.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResponse(int status, String mensagem, List<ErroCampo> erros) {

    public static ErroResponse respostaPadrao(String mensagem){
        return new ErroResponse(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroResponse conjflito(String mensagem){
        return new ErroResponse(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }

}
