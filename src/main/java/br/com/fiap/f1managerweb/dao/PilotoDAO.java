package br.com.fiap.f1managerweb.dao;

import br.com.fiap.f1managerweb.models.Piloto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PilotoDAO { //DAO Conversa com o Banco

    private Connection conexao; //Connection é a conexão com o banco

    //CADASTRAR - CREATE
    public void cadastrar(Piloto piloto) {

        conexao = ConnectionFactory.obterConexao(); //Pega os dados de obterConexao() = dados de login do banco

        try {
            String sql = """
                    INSERT INTO TBL_PILOTO_F1
                    (NOME, NUMERO, EQUIPE, NACIONALIDADE, PONTOS)
                    VALUES (?, ?, ?, ?, ?)
                    """;

            PreparedStatement ps = conexao.prepareStatement(sql); //Prepara SQL para a execução

            ps.setString(1, piloto.getNome());
            ps.setInt(2, piloto.getNumero());
            ps.setString(3, piloto.getEquipe());
            ps.setString(4, piloto.getNacionalidade());
            ps.setDouble(5, piloto.getPontos());

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //BUSCAR
    public Piloto buscarPorNumero(int numero) {

        conexao = ConnectionFactory.obterConexao();

        Piloto piloto = null;

        try {
            String sql = """
                    SELECT * FROM TBL_PILOTO_F1
                    WHERE NUMERO = ?
                    """;

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, numero);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                piloto = new Piloto();

                piloto.setNome(rs.getString("NOME"));
                piloto.setNumero(rs.getInt("NUMERO"));
                piloto.setEquipe(rs.getString("EQUIPE"));
                piloto.setNacionalidade(rs.getString("NACIONALIDADE"));
                piloto.setPontos(rs.getDouble("PONTOS"));
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return piloto;
    }

    //LISTAR
    public List<Piloto> listar() {

        conexao = ConnectionFactory.obterConexao();

        List<Piloto> pilotos = new ArrayList<>();

        try {
            String sql = "SELECT * FROM TBL_PILOTO_F1";

            PreparedStatement ps = conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Piloto piloto = new Piloto();

                piloto.setNome(rs.getString("NOME"));
                piloto.setNumero(rs.getInt("NUMERO"));
                piloto.setEquipe(rs.getString("EQUIPE"));
                piloto.setNacionalidade(rs.getString("NACIONALIDADE"));
                piloto.setPontos(rs.getDouble("PONTOS"));

                pilotos.add(piloto);
            }

            rs.close();
            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pilotos;
    }

    //ALTERAR | UPDATE
    public void alterar(Piloto piloto) {

        conexao = ConnectionFactory.obterConexao();

        try {
            String sql = """
                    UPDATE TBL_PILOTO_F1
                    SET NOME = ?,
                        EQUIPE = ?,
                        NACIONALIDADE = ?,
                        PONTOS = ?
                    WHERE NUMERO = ?
                    """;

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, piloto.getNome());
            ps.setString(2, piloto.getEquipe());
            ps.setString(3, piloto.getNacionalidade());
            ps.setDouble(4, piloto.getPontos());
            ps.setInt(5, piloto.getNumero());

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //EXCLUIR | REMOVE
    public void excluir(int numero) {

        conexao = ConnectionFactory.obterConexao();

        //try catch tratam erros que podem acontecer durante o andamento do programa
        try {
            String sql = """
                    DELETE FROM TBL_PILOTO_F1
                    WHERE NUMERO = ?
                    """;

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, numero);

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) { //SQLException representa qualquer erro relacionado ao Banco de Dados | 'e' é uma variável que guarda os detalhes do erro
            //Impede que continue com os erros que apareceram
            throw new RuntimeException(e); //RuntimeException = pacote | 'e' = conteúdo dentro do pacote

        }
    }
}