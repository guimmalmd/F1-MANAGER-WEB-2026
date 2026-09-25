package br.com.fiap.f1managerweb.controller;

import br.com.fiap.f1managerweb.models.Piloto;
import br.com.fiap.f1managerweb.service.PilotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController //Diz ao Spring que a classe recebe requisições web e devolve respostas
@RequestMapping("/pilotos") //Define o endereço base do Controller | Nesse caso todos os métodos começam com /pilotos

public class PilotoController { //O Controller recebe os pedidos HTTP | Porta de entrada da API
    /*
    Recebe requisições HTTP como:
    GET - Pede para ver
    POST - Cria
    PUT - Atualiza
    DELETE - Exclui
    */

    /*
    Controller - Recebe o pedido
    Service - Decide se pode
    DAO - mexe no banco
    */

    private final PilotoService pilotoService = new PilotoService(); //Controller não deve conversar diretamente com o DAO, o correto é Controller - Service - DAO | Então ele pede que o Service resolva para ele

    //
    @GetMapping
    public ResponseEntity<List<Piloto>> listar() {
        return ResponseEntity.ok(pilotoService.listar()); //Aqui ele pede para o Service e o Service pede ao DAO
    }
    //ResponseEntity representa toda a resposta HTTP que o servidor envia de volta para o cliente
    //O 'ok' é do status HTTP, ex: 200 OK

    //Define que começa com /numero | ex: /pilotos/12
    @GetMapping("/{numero}")
    public ResponseEntity<Piloto> buscarPorNumero(@PathVariable int numero) { //PathVariable pega numero que veio na URL

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