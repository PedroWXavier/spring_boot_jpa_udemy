package org.example.spring_boot_data_udemy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Data
//@Getter // Anotacao do pacote lombok que cria os getters
//@Setter // Anotacao do pacote lombok que cria os setters
@ToString(exclude = "livros")
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    // Aqui nao temos a anotacao Colum, pois nao se trata de uma coluna no banco de dados
    // Eh apenas um relacionamento que podemos criar no nosso objeto Autor
    @OneToMany(mappedBy = "autor")
//    @Transient
    private List<Livro> livros;

}
