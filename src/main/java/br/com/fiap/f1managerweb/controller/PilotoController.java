package br.com.fiap.f1managerweb.controller;

import br.com.fiap.f1managerweb.models.Piloto;
import br.com.fiap.f1managerweb.service.PilotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/pilotos") //Define o começo de todas as rotas
public class PilotoController {

    private final PilotoService pilotoService = new PilotoService();

    @GetMapping
    public ResponseEntity<List<Piloto>> listar() {
        return ResponseEntity.ok(pilotoService.listar());
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Piloto> buscarPorNumero(@PathVariable int numero) {

        Piloto piloto = pilotoService.buscarPorNumero(numero);

        if (piloto != null) {
            return ResponseEntity.ok(piloto);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody Piloto piloto) { //RequestBody pega o objeto enviado no corpo da requisição

        try {

            pilotoService.cadastrar(piloto);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Piloto cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao cadastrar piloto: " + e.getMessage());
        }
    }

    @PutMapping("/{numero}")
    public ResponseEntity<Void> atualizar(
            @PathVariable int numero,
            @RequestBody Piloto piloto) {

        try {
            pilotoService.atualizar(numero, piloto);
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> excluir(@PathVariable int numero) {

        try {
            pilotoService.excluir(numero);
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}