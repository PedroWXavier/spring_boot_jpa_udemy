package org.example.spring_boot_data_udemy.repository;

import org.example.spring_boot_data_udemy.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
