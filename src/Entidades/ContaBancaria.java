package Entidades;

public abstract class ContaBancaria{
    private int numConta, agencia;
    private String banco;
    private double saldoConta;

    public ContaBancaria(){

    }

    public abstract boolean sacar(double valor, ContaBancaria conta, boolean transferencia);

    public abstract void depositar(double valor, ContaBancaria conta, boolean transferencia);

    public abstract void transferir(double valor, ContaBancaria conta1, ContaBancaria conta2);

    public int getNumConta() {
        return numConta;
    }

    public void setNumConta(int numConta) {
        this.numConta = numConta;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public double getSaldoConta() {
        return saldoConta;
    }

    public void setSaldoConta(double saldoConta) {
        this.saldoConta = saldoConta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
}
