package org.example.spring_boot_data_udemy.repository;

import org.example.spring_boot_data_udemy.model.Autor;
import org.example.spring_boot_data_udemy.model.GeneroLivro;
import org.example.spring_boot_data_udemy.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;
    @Autowired
    private AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("123456789");
        livro.setTitulo("Clean Code");
        livro.setDataPublicacao(LocalDate.of(2008, 8, 1));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(150));

        Autor autor = autorRepository
                .findById(UUID.fromString("df917936-2eed-450b-982a-cf9e4c3647b4"))
                .orElse(null);

        livro.setAutor(autor);

        Livro salvo = repository.save(livro);
        System.out.println("Livro salvo: " + salvo);
    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("123456789");
        livro.setTitulo("Clean Code");
        livro.setDataPublicacao(LocalDate.of(2008, 8, 1));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(150));

        Autor autor = new Autor();
        autor.setNome("Pedro2");
        autor.setDataNascimento(LocalDate.of(1999, 8, 9));
        autor.setNacionalidade("BR");

        livro.setAutor(autor);

        Livro salvo = repository.save(livro);
        System.out.println("Livro salvo: " + salvo);
    }

    @Test
    @Transactional
    void buscarLivroLazyTest(){
        UUID id = UUID.fromString("56b3fa22-f058-4aed-bee4-2ce1cc2a4bff");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void buscarLivrosPorTituloTest(){
        List<Livro> livros = repository.findByTitulo("Clean Code");
        livros.forEach(livro -> System.out.println("Livro: " + livro));
    }

    @Test
    void buscarTodosOsLivrosQueryPersonalizada(){
        List<Livro> livros = repository.listarTodosOrdenadoPorTituloAndPreco();
        livros.forEach(livro -> System.out.println("Livro: " + livro));
    }

    @Test
    void ListarAutoresDosLivros(){
        List<Autor> autores = repository.listarAutoresDosLivros();
        autores.forEach(a -> System.out.println("Autor: " + a));
    }

    @Test
    void ListarGenerosDeLivrosDeAutoresBrasileiros(){
        List<String> resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarLivrosPorGenero(){
        List<Livro> resultado = repository.findByGenero(GeneroLivro.FANTASIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarLivrosPorGeneroPositionalParameters(){
        List<Livro> resultado = repository.findByGeneroPositionalParameters(GeneroLivro.FANTASIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void updateTituloLivro(){
        repository.updateByTitulo("Clean Code", "Clean Code 2");
    }

}
