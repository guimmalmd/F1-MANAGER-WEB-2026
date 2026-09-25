package br.com.fiap.f1managerweb.service;

import br.com.fiap.f1managerweb.dao.PilotoDAO;
import br.com.fiap.f1managerweb.models.Piloto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Só avisa o Spring que essa classe representa a camada de serviço
public class PilotoService { //Service aplica regras

    private final PilotoDAO pilotoDAO; //Depois de validar as regras, o Service faz pedidos ao DAO para fazer determinadas ações dentro do banco
    //Service decide | DAO executa no banco

    public PilotoService() {
        this.pilotoDAO = new PilotoDAO();
    }

    //LISTAR
    public List<Piloto> listar() {
        return pilotoDAO.listar(); //Service só pede para listar os pilotos
    }

    //BUSCAR
    public Piloto buscarPorNumero(int numero) {
        return pilotoDAO.buscarPorNumero(numero);
    }

    //CADASTRAR - CREATE
    public void cadastrar(Piloto piloto) {

        if (piloto.getNumero() == 0) {
            throw new IllegalArgumentException("Número do piloto é obrigatório");
        }

        if (piloto.getNome() == null || piloto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do piloto é obrigatório");
        }

        pilotoDAO.cadastrar(piloto);
    }

    //ATUALIZAR | UPDATE
    public void atualizar(int numero, Piloto piloto) {

        if (numero != piloto.getNumero()) {
            throw new IllegalArgumentException(
                    "O número do piloto não corresponde ao número informado"
            );
        }

        Piloto pilotoExiste = buscarPorNumero(numero);

        if (pilotoExiste == null) {
            throw new IllegalArgumentException("Piloto não encontrado");
        }

        pilotoDAO.alterar(piloto);
    }

    //EXCLUIR
    public void excluir(int numero) {

        Piloto pilotoExiste = buscarPorNumero(numero);

        if (pilotoExiste == null) {
            throw new IllegalArgumentException("Piloto não encontrado");
        }

        pilotoDAO.excluir(numero);
    }
}