package br.com.fiap.f1managerweb.service;

import br.com.fiap.f1managerweb.dao.PilotoDAO;
import br.com.fiap.f1managerweb.models.Piloto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Avisa o Spring que essa classe representa a camada de serviço
public class PilotoService {

    private final PilotoDAO pilotoDAO;

    public PilotoService() {
        this.pilotoDAO = new PilotoDAO();
    }

    public List<Piloto> listar() {
        return pilotoDAO.listar();
    }

    public Piloto buscarPorNumero(int numero) {
        return pilotoDAO.buscarPorNumero(numero);
    }

    public void cadastrar(Piloto piloto) {

        if (piloto.getNumero() == 0) {
            throw new IllegalArgumentException("Número do piloto é obrigatório");
        }

        if (piloto.getNome() == null || piloto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do piloto é obrigatório");
        }

        pilotoDAO.cadastrar(piloto);
    }

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

    public void excluir(int numero) {

        Piloto pilotoExiste = buscarPorNumero(numero);

        if (pilotoExiste == null) {
            throw new IllegalArgumentException("Piloto não encontrado");
        }

        pilotoDAO.excluir(numero);
    }
}