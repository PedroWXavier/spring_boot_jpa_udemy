package org.example.spring_boot_data_udemy.controller;

import org.example.spring_boot_data_udemy.controller.dto.AutorRequestDTO;
import org.example.spring_boot_data_udemy.controller.dto.AutorResponseDTO;
import org.example.spring_boot_data_udemy.controller.dto.ErroResponse;
import org.example.spring_boot_data_udemy.exceptions.RegistroDuplicadoException;
import org.example.spring_boot_data_udemy.model.Autor;
import org.example.spring_boot_data_udemy.service.AutorService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
// http://localhost:8080/autores
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service){
        this.service = service;
    }
    
    @PostMapping
    // @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> salvar(@RequestBody AutorRequestDTO autor){
        try {
            Autor autorEntidade = autor.mapearParaAutor();
            service.salvar(autorEntidade);

            // http://localhost:8080/autores/{id}
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResponse.conjflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorResponseDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();

            var dto = new AutorResponseDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());

            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        service.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> filtrar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        List<Autor> resultado = service.pesquisa(nome, nacionalidade);
        List<AutorResponseDTO> lista = resultado
                .stream()
                .map(autor -> new AutorResponseDTO(autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody AutorRequestDTO autorDTO){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(autorDTO.nome());
        autor.setDataNascimento(autorDTO.dataNascimento());
        autor.setNacionalidade(autorDTO.nacionalidade());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}
