package org.example.spring_boot_data_udemy.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AutorResponseDTO(
        UUID id,
        String nome,
        LocalDate dataNascimento,
        String nacionalidade) {
}
