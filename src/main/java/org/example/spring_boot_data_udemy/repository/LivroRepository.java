package org.example.spring_boot_data_udemy.repository;

import org.example.spring_boot_data_udemy.model.Autor;
import org.example.spring_boot_data_udemy.model.GeneroLivro;
import org.example.spring_boot_data_udemy.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * LivroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //Query Method
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // JPQL -> referencia as entidades e propriedades
    // select l.* from livro l order by l.titulo, l.preco
    @Query(" select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    /**
     * select a *
     * from livro l
     * join autor a on l.autor_id = a.id
     */
    @Query(" select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    @Query(" select distinct l.titulo from Livro l ")
    List<String> listarNomesDiferentesDeLivros();

    @Query("""
        select distinct l.genero
        from Livro l
        join l.autor a
        where a.nacionalidade = 'BR'
        order by l.genero
    """)
    List<String> listarGenerosAutoresBrasileiros();

    // named parameters
    @Query(" select l from Livro l where l.genero = :genero order by :paramOrdenacao ")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro, @Param("paramOrdenacao") String paramOrdenacao);

    // positional parameters
    @Query(" select l from Livro l where l.genero = ?1 order by ?2 ")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String paramOrdenacao);

    @Modifying
    @Transactional
    @Query(" delete from Livro l where l.titulo = ?1 ")
    void deleteByTitulo(String titulo);

    @Modifying
    @Transactional
    @Query(" update Livro l set l.titulo = ?2 where l.titulo = ?1 ")
    void updateByTitulo(String titulo, String novoTitulo);

    boolean existsByAutor(Autor autor);
}
