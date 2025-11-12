package org.example.spring_boot_data_udemy.controller.dto;

import org.example.spring_boot_data_udemy.model.Autor;

import java.time.LocalDate;

public record AutorRequestDTO(
        String nome,
        LocalDate dataNascimento,
        String nacionalidade) {

    public  Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}
