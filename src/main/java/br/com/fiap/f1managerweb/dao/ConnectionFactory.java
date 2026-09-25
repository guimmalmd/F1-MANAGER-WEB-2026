package br.com.fiap.f1managerweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory { //Classe responsável para criar a conexão com o banco

    public static Connection obterConexao() {

        Connection conexao = null;

        try {
            //DriverManager abre a conexão com o banco
            conexao = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl",
                    "RM571713",
                    "250807"
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conexao;
    }
}