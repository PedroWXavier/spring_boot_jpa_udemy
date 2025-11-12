package org.example.spring_boot_data_udemy.service;

import org.example.spring_boot_data_udemy.model.Autor;
import org.example.spring_boot_data_udemy.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository){
        this.repository = repository;
    }

    public Autor salvar(Autor autor){
        return repository.save(autor);
    }
}
