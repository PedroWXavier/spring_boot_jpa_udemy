package org.example.spring_boot_data_udemy.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.example.spring_boot_data_udemy.model.Autor;

import java.time.LocalDate;

public record AutorRequestDTO(
        @NotBlank(message = "campo obrigatorio") // Utilizar @NotBlank em strings
        @Size(max = 100, message = "o nome deve conter no maximo 100 caracteres")
        String nome,

        @NotNull(message = "campo obrigatorio")
        @Past(message = "nao pode ser uma data atual ou futura")
        LocalDate dataNascimento,

        @NotBlank(message = "campo obrigatorio")
        @Size(max = 50, message = "o nome deve conter no maximo 50 caracteres")
        String nacionalidade) {

    public  Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
