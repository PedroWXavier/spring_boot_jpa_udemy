package org.example.spring_boot_data_udemy.repository;

import org.example.spring_boot_data_udemy.model.Autor;
import org.example.spring_boot_data_udemy.model.GeneroLivro;
import org.example.spring_boot_data_udemy.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Bruno");
        autor.setDataNascimento(LocalDate.of(1999, 8, 9));
        autor.setNacionalidade("BR");

        Autor autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        UUID id = UUID.fromString("79bc42cf-2c36-4576-99fd-b283b964056d");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Autor encontrado: " + autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1998, 8, 9));
            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
//        lista.forEach(System.out::println); Pode ser feito desta forma tambem, o print usara o toString do objeto
        lista.forEach(autor -> System.out.println("Autor encontrado: " + autor));
    }

    @Test
    public void countTest(){
        System.out.println("Total de autores: " + repository.count());
    }

    @Test
    public void deletarByIdTest(){
        UUID id = UUID.fromString("79bc42cf-2c36-4576-99fd-b283b964056d");
        repository.deleteById(id);
    }

    @Test
    public void deletarTest(){
        UUID id = UUID.fromString("15eddf8d-5075-45ee-9c7e-430b049fb9f3");
        var usuario = repository.findById(id).get();

        repository.delete(usuario);
    }

    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Pedro");
        autor.setDataNascimento(LocalDate.of(1999, 8, 9));
        autor.setNacionalidade("BR");

        Livro livro = new Livro();
        livro.setIsbn("3200-9800");
        livro.setTitulo("A will eternal");
        livro.setDataPublicacao(LocalDate.of(2012, 9, 21));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("3200-9800");
        livro2.setTitulo("Beyond the timescape");
        livro2.setDataPublicacao(LocalDate.of(2013, 10, 24));
        livro2.setGenero(GeneroLivro.FANTASIA);
        livro2.setPreco(BigDecimal.valueOf(250));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(List.of(livro, livro2));
    }
}
