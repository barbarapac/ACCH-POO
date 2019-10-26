package DAO;

import Conexao.Conexao;
import Entidades.ContaBancaria;
import Entidades.ContaCorrente;
import Entidades.ContaPoupanca;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOContaBancaria {

    private Conexao conexao;


    public DAOContaBancaria() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
        conexao = new Conexao();
    }

    public void cadastrarContaBancaria(int numConta, int agencia, String banco, double saldoConta, String tipoConta, double limiteCredito) throws SQLException{

        String sql = "INSERT INTO conta_bancaria (agencia, numeroconta, banco, saldo, tipoconta, limitecredito) " +
                "VALUES (" + agencia + ", " + numConta +  ", '" + banco + "', " + saldoConta + ", '" + tipoConta + "'," + limiteCredito +");";

        Statement stm = conexao.getConnection().createStatement();

        boolean res = stm.execute(sql);

        if(res == false) {
            System.out.println("");
            System.out.println("| ******************************************* |");
            System.out.println("|        Conta  cadastrada com sucesso!       |");
            System.out.println("| ******************************************* |");
            System.out.println("");
        }else {
            System.out.println("Erro ao cadastrar conta.");
        }

    }

    public void deletarContaBancaria(int parmNumConta) throws SQLException {
        String sql = "delete from movimento where numeroconta = " + parmNumConta + ";"
                + "delete from conta_bancaria where numeroconta = " + parmNumConta;

        Statement stm = conexao.getConnection().createStatement();

        boolean res = stm.execute(sql);

        if(!res) {
            System.out.println("");
            System.out.println("| ***************************************** |");
            System.out.println("|         Conta deletada com sucesso!       |");
            System.out.println("| ***************************************** |");
            System.out.println("");
        }else {
            System.out.println("Erro ao cadastrar conta.");
        }

    }

    public ContaCorrente buscarContaCorrente(int parmNumConta) throws SQLException {
        String sql = "select * from conta_bancaria where numeroconta = " + parmNumConta + " and tipoconta = 'CC'";

        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);

        try {
            while(rs.next()) {
                ContaCorrente c = new ContaCorrente(rs.getInt("numeroconta"), rs.getInt("agencia"), rs.getString("banco"), rs.getDouble("saldo"));

                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ContaPoupanca buscarContaPoupanca(int parmNumConta) throws SQLException {
        String sql = "select * from conta_bancaria where numeroconta = " + parmNumConta + " and tipoconta = 'CP'";

        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);


        try {
            while(rs.next()) {
                ContaPoupanca c = new ContaPoupanca(rs.getInt("numeroconta"), rs.getInt("agencia"), rs.getString("banco"), rs.getDouble("saldo"), rs.getDouble("limitecredito"));

                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int verificaDuplicidadeConta(int parmNumConta) throws SQLException {
        String sql = "select count(numeroconta) as countConta from conta_bancaria where numeroconta = " + parmNumConta;

        Statement stm = conexao.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);

        try {
            while(rs.next()) {
                return rs.getInt("countConta");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public void atualizaSaldoConta(ContaBancaria conta, double valorMovimento, String tipoMovimento) throws SQLException {
        String sql =
                "update conta_bancaria set saldo = " +  conta.getSaldoConta() + " where numeroconta = " + conta.getNumConta() + ";" +
                "insert into movimento (tipomovimento, numeroconta, valormovimento) values ('" + tipoMovimento + "', " + conta.getNumConta() +
                        ", " +  valorMovimento + ")";

        Statement stm = conexao.getConnection().createStatement();
        boolean res = stm.execute(sql);

        if(res) {
            System.out.println("Erro ao realizar movimento.");
        }

    }

    public void gerarExtrato(int parmNumConta) throws SQLException {
        String sqlm = "select " +
                        "case " +
                            "when m.tipomovimento = 'S' then 'SAQUE' " +
                            "when m.tipomovimento = 'D' then 'DEPÓSITO'" +
                            "when m.tipomovimento = 'T' then 'TRANSF.'" +
                        "end as tipomovimento," +
                        "m.valormovimento," +
                        "m.dtmovimento," +
                        "c.agencia," +
                        "c.banco," +
                        "c.saldo," +
                        "c.tipoconta," +
                        "c.numeroconta" +
                    " from " +
                        "conta_bancaria c join movimento as m on " +
                            " c.numeroconta = m.numeroconta " +
                    " where " +
                        " c.numeroconta = " + parmNumConta;

        String sqlc = "select * from conta_bancaria where numeroconta = " + parmNumConta;

        Statement stmM = conexao.getConnection().createStatement();
        Statement stmC = conexao.getConnection().createStatement();

        ResultSet resc = stmC.executeQuery(sqlc);
        ResultSet resm = stmM.executeQuery(sqlm);

        try {
            while(resc.next()) {
                System.out.println("|                                                                               |");
                System.out.println("| CONTA: " + resc.getInt("numeroconta") + "     AGÊNCIA: " + resc.getInt("agencia") + "     BANCO: " + resc.getString("banco"));
                System.out.println("| SALDO ATUAL: " + resc.getDouble("saldo"));
                System.out.println("| ------------------------------- Movimentações ------------------------------- |");
                System.out.println("|    Data           MOVIMENTO            VALOR" );
            }
            while(resm.next()) {
                System.out.println("| " + resm.getDate("dtmovimento") + "          " + resm.getString("tipomovimento")+ "             R$: " + resm.getDouble("valormovimento") );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
