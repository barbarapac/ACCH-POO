package Entidades;

import DAO.DAOContaBancaria;

import java.sql.SQLException;
import java.util.Scanner;

public class ContaPoupanca extends ContaBancaria implements Imprimivel{

    private double limiteCredito;
    private static String tipoConta = "CP";

    DAOContaBancaria DAO = new DAOContaBancaria();

    public ContaPoupanca(int numConta, int agencia, String banco, double saldoConta, double limiteCredito) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        super();
        this.setNumConta(numConta);
        this.setAgencia(agencia);
        this.setBanco(banco);
        this.setSaldoConta(saldoConta);
        this.limiteCredito = limiteCredito;
    }

    public ContaPoupanca() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        int sair = 1, encerraSubMenu =1;
        String opcaoSubMenu = "";

        Scanner sInt = new Scanner(System.in);
        Scanner sString = new Scanner(System.in);

        do {
            do {
                try{
                    System.out.println("| ------------------------------------------------------------------------------------------------------------------------------------------------------------------- |");
                    System.out.println("| 1 - Cadastrar Conta Corrente || 2 - Excluir uma Conta Corrente || 3 - Realizar Saque || 4 - Realizar Deposito || 5 - Transferencia || 6 - Relatório || 0 - SAIR     |");
                    System.out.println("| ------------------------------------------------------------------------------------------------------------------------------------------------------------------- |");
                    System.out.printf("| Escolha a operação: ");

                    opcaoSubMenu = sString.nextLine();
                } catch (Exception e){
                    System.out.println("Você não digitou um numero!");
                    opcaoSubMenu = "7";
                }
            }while (opcaoSubMenu == "7");

            switch (opcaoSubMenu) {
                case "0":
                    sair = 0;
                    break;
                case "1":
                    do {
                        try {
                            System.out.println("| ----------------------------------------------------------------------------------------------------------- |");
                            System.out.println("| ---------------------------------------- CADASTRO CONTA POUPANÇA ------------------------------------------ |");
                            System.out.println("| ----------------------------------------------------------------------------------------------------------- |");
                            System.out.println("|                                                                                                             |");

                            System.out.printf("| Numero Conta: ");
                            setNumConta(sInt.nextInt());
                        } catch (Exception e) {
                            encerraSubMenu = 0;
                        }

                        if (DAO.verificaDuplicidadeConta(getNumConta()) >= 1) {
                            System.out.println("");
                            System.out.println("| --------------------------------- |");
                            System.out.println("|    ATENÇÃO: Conta já cadastrada!  |");
                            System.out.println("| --------------------------------- |");
                            System.out.println("");
                            encerraSubMenu = 0;
                        } else {
                            try {
                                System.out.printf("| Agência: ");
                                setAgencia(sInt.nextInt());
                                System.out.printf("| Banco: ");
                                setBanco(sString.nextLine());
                                System.out.printf("| Saldo: ");
                                setSaldoConta(sInt.nextDouble());
                                System.out.printf("| Limite crédito: ");
                                limiteCredito = (sInt.nextDouble());
                            } catch (Exception e) {
                                System.out.println("Erro na leitura!");
                            }

                            DAO.cadastrarContaBancaria(getNumConta(), getAgencia(), getBanco(), getSaldoConta(), tipoConta, limiteCredito);

                            encerraSubMenu = 0;
                        }
                    } while (encerraSubMenu == 1);
                    break;
                case "2":
                    do {
                        System.out.println("| ----------------------------------------------------------------------------------------------------------- |");
                        System.out.println("| ----------------------------------------- EXCLUIR CONTA POUPANÇA ------------------------------------------ |");
                        System.out.println("| ----------------------------------------------------------------------------------------------------------- |");
                        System.out.println("|                                                                                                             |");

                        System.out.printf("| Digite o numero da conta corrente: ");

                        int parmNumConta = sInt.nextInt();

                        ContaBancaria existeConta = mostrarDados(parmNumConta);

                        if(existeConta != null){
                            System.out.println("| --------------------------------------------------------------------------- |");
                            System.out.println("|  Deseja excluir conta poupança? (1-sim/0-não):                              |");
                            System.out.println("| --------------------------------------------------------------------------- |");
                            System.out.printf("| Resposta: ");

                            int delConta = sInt.nextInt();

                            if(delConta == 1){
                                DAO.deletarContaBancaria(parmNumConta);
                            }

                            encerraSubMenu = 0;
                        }else{
                            encerraSubMenu = 0;
                        }
                    } while (encerraSubMenu == 1);
                    break;
                case "3":
                    do {
                        System.out.println("| ----------------------------------------------------------------------------------------------------------- |");
                        System.out.println("| ------------------------------------------ SAQUE CONTA POUPANÇA ------------------------------------------- |");
                        System.out.println("| ----------------------------------------------------------------------------------------------------------- |");
                        System.out.println("|                                                                                                             |");

                        System.out.printf("| Digite o numero da conta poupança: ");
                        int parmNumConta = sInt.nextInt();

                        ContaBancaria conta = mostrarDados(parmNumConta);

                        if(conta != null){
                            System.out.println("| --------------------------------------------------------------------------- |");
                            System.out.println("|  Digite o valor:                                                            |");
                            System.out.println("| --------------------------------------------------------------------------- |");
                            System.out.printf("| Resposta: ");

                            double valorSaque = sInt.nextDouble();

                            sacar(valorSaque, conta, false);

                            encerraSubMenu = 0;
                        }else{
                            encerraSubMenu = 0;
                        }

                    } while (encerraSubMenu == 1);
                    break;
                case "4":
                    do {
                        System.out.println("| -------------------------------------------------------------------------------------------------------------- |");
                        System.out.println("| ------------------------------------------ DEPÓSITO CONTA POUPANÇA ------------------------------------------- |");
                        System.out.println("| -------------------------------------------------------------------------------------------------------------- |");
                        System.out.println("|                                                                                                                |");

                        System.out.printf("| Digite o numero da conta poupança: ");
                        int parmNumConta = sInt.nextInt();

                        ContaBancaria conta = mostrarDados(parmNumConta);

                        if(conta != null){
                            System.out.println("| --------------------------------------------------------------------------- |");
                            System.out.println("|  Digite o valor:                                                            |");
                            System.out.println("| --------------------------------------------------------------------------- |");
                            System.out.printf("| Resposta: ");

                            double valorDeposito = sInt.nextDouble();

                            depositar(valorDeposito, conta, false);

                            encerraSubMenu = 0;
                        }else{
                            encerraSubMenu = 0;
                        }
                    } while (encerraSubMenu == 1);
                    break;
                case "5":
                    do {
                        System.out.println("| ----------------------------------------------------------------------------------------------------------------- |");
                        System.out.println("| ------------------------------------------ TRANSFERENCIA ENTRE CONTAS ------------------------------------------- |");
                        System.out.println("| ----------------------------------------------------------------------------------------------------------------- |");
                        System.out.println("|                                                                                                                   |");

                        System.out.printf("| Digite o numero da conta proprietário: ");
                        int parmContaProprietario = sInt.nextInt();

                        ContaBancaria contaProprietario = mostrarDados(parmContaProprietario);

                        if(contaProprietario != null){
                            System.out.println("| --------------------------------------------------------------------------- |");
                            System.out.printf("| Digite o numero da conta destinatário: ");
                            int parmContaDestinatario = sInt.nextInt();

                            ContaBancaria contaDestinatario = mostrarDados(parmContaDestinatario);

                            if(contaDestinatario != null){
                                System.out.println("| --------------------------------------------------------------------------- |");
                                System.out.println("|  Digite o valor da transferencia:                                           |");
                                System.out.println("| --------------------------------------------------------------------------- |");
                                System.out.printf("| Resposta: ");

                                double valorTransferencia = sInt.nextDouble();

                                transferir(valorTransferencia, contaProprietario, contaDestinatario);

                                encerraSubMenu = 0;
                            }else {
                                encerraSubMenu = 0;
                            }
                        }


                    } while (encerraSubMenu == 1);
                    break;
                case "6":
                    do {
                        System.out.println("| ----------------------------------------------------------------------------- |");
                        System.out.println("|                                    EXTRATO                                    |");
                        System.out.println("| ----------------------------------------------------------------------------- |");
                        System.out.println("|                                                                               |");
                        System.out.printf("| Digite o numero da conta: ");
                        int parmNumconta = sInt.nextInt();

                        ContaPoupanca resultConta = null;

                        try {
                            resultConta = DAO.buscarContaPoupanca(parmNumconta);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        if(resultConta == null){
                            System.out.println("");
                            System.out.println("| ------------------------------------------ |");
                            System.out.println("|       ATENÇÃO: Conta não encontrada!       |");
                            System.out.println("| ------------------------------------------ |");
                            System.out.println("");
                        }else {
                            DAO.gerarExtrato(parmNumconta);
                            encerraSubMenu = 0;
                            System.out.println("| ----------------------------------------------------------------------------- |");
                        }
                    } while (encerraSubMenu == 1);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (sair == 1);

        return;
    }

    @Override
    public boolean sacar(double valor, ContaBancaria conta, boolean transferencia) {
        boolean retorno = true;
        String tipoMovimento = "S";

        if(transferencia){
            tipoMovimento = "T";
        }


        if (valor <= conta.getSaldoConta()){
            conta.setSaldoConta(conta.getSaldoConta() - valor);

            try {
                DAO.atualizaSaldoConta(conta, valor, tipoMovimento);
                if(!transferencia){
                    System.out.println("");
                    System.out.println("| ************************************** |");
                    System.out.println("|      Saque realizado com sucesso!      |");
                    System.out.println("| ************************************** |");
                    System.out.println("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                ContaPoupanca cp = DAO.buscarContaPoupanca(conta.getNumConta());

                if (valor <= (cp.getLimiteCredito() + conta.getSaldoConta())) {
                    double valorLimite, valorSaldoConta;

                    valorLimite = cp.getLimiteCredito() + conta.getSaldoConta() - valor;
                    valorSaldoConta = (cp.getLimiteCredito() - valorLimite) * -1;

                    conta.setSaldoConta(valorSaldoConta);

                    try {
                        DAO.atualizaSaldoConta(conta, valor, tipoMovimento);
                        if(!transferencia){
                            System.out.println("");
                            System.out.println("| *************************************** |");
                            System.out.println("|       Saque realizado com sucesso!      |");
                            System.out.println("| *************************************** |");
                            System.out.println("");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("");
                    System.out.println("| ------------------------------------------ |");
                    System.out.println("|  ATENÇÃO: Saldo insuficiente para saque!   |");
                    System.out.println("| ------------------------------------------ |");
                    System.out.println("");

                    retorno = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        return retorno;

    }

    @Override
    public void depositar(double valor, ContaBancaria conta, boolean transferencia) {
        String tipoMovimento = "D";

        if(transferencia){
            tipoMovimento = "T";
        }


        conta.setSaldoConta(conta.getSaldoConta() + valor);


        try {
            DAO.atualizaSaldoConta(conta, valor, tipoMovimento);
            if(transferencia == false){
                System.out.println("");
                System.out.println("| ************************************** |");
                System.out.println("|     Depósito realizado com sucesso!    |");
                System.out.println("| ************************************** |");
                System.out.println("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transferir(double valor, ContaBancaria contaProprietario, ContaBancaria contaDestinatario) {
        boolean autorizaSaque = sacar(valor, contaProprietario, true);

        if(autorizaSaque){
            depositar(valor, contaDestinatario, false);

            System.out.println("");
            System.out.println("| ******************************************* |");
            System.out.println("|     Trânsferencia realizada com sucesso!    |");
            System.out.println("| ******************************************* |");
            System.out.println("");
        }
    }

    @Override
    public ContaBancaria mostrarDados(int parmNumconta) {
        ContaPoupanca resultConta = null;


        try {
            resultConta = DAO.buscarContaPoupanca(parmNumconta);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(resultConta == null){
            System.out.println("");
            System.out.println("| ------------------------------------------ |");
            System.out.println("|       ATENÇÃO: Conta não encontrada!       |");
            System.out.println("| ------------------------------------------ |");
            System.out.println("");

            return null;
        }else {
            System.out.println("| --------------------------------------------------------------- |");
            System.out.println("| ----------------------------- CONTA --------------------------- |");
            System.out.println("| --------------------------------------------------------------- |");
            System.out.println("|                                                                 |");
            System.out.println("| Numero conta: " + resultConta.getAgencia() + "| Agência: " + resultConta.getAgencia());
            System.out.println("| Banco: " + resultConta.getBanco() + "| Saldo: " + resultConta.getSaldoConta() + "| Limite: " + resultConta.limiteCredito);

            return resultConta;
        }


    }

    public double getLimiteCredito() {
        return limiteCredito;
    }
}
