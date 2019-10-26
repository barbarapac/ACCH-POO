package Conexao;

import java.sql.*;

public class Conexao {

    private String url;
    private String usuario;
    private String senha;
    private Connection con;

    @SuppressWarnings("all")
    public Conexao(){
        url = "jdbc:postgresql://localhost:5432/acch";
        usuario = "postgres";
        senha = "postgres";

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, usuario, senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }

}
