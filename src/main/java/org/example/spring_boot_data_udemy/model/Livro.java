package org.example.spring_boot_data_udemy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro", schema = "public")
@Data // Anotacao do pacote lombok implementa 5 anotacoes
//@Getter @Setter implementam os getters e setters
//@ToString implementa o metodo toString com todos os atributos
//@EqualsAndHashCode implementa o metodo equals e hashCode para comparar objetos
//@RequiredArgsConstructor implementa um construtor que recebe todos os atributos final
// Os proximos dois podem ser uteis em algum outro momento
//@NoArgsConstructor implementa um construtor vazio
//@AllArgsConstructor implementa um construtor que recebe todos os atributos
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING) // Boa pratica utilizar como String
//    @Enumerated(EnumType.ORDINAL) Desta forma estamos guardando a posicao do enum no banco de dados
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

//    @Column(name = "id_autor") Pode ser feito desta forma, mas eh preferivel que se use o objeto Autor
//    private UUID idAutor;

    @ManyToOne(fetch = FetchType.LAZY)
    //@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
