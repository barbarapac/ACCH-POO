/**
 * @author Bárbara Pacheco, Sara Vendramini
 */


import Entidades.ContaCorrente;
import Entidades.ContaPoupanca;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String opcaoEntrada = "";
        int encerrar = 1;
        Scanner sInt = new Scanner(System.in);

        do {
            do {
                try{
                    System.out.println("| --------------------------------------------------------------------------------- |");
                    System.out.println("| 1 - Conta Corrente || 2 - Conta Poupança || 0 - Sair                              |");
                    System.out.println("| --------------------------------------------------------------------------------- |");
                    System.out.printf("| Escolha a operação: ");

                    opcaoEntrada = sInt.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("Você não digitou um numero!");
                    opcaoEntrada = "4";
                }
            }while (opcaoEntrada == "4");


            switch (opcaoEntrada){
                case "0":
                    encerrar = 0;
                    break;
                case "1":
                    ContaCorrente cc = new ContaCorrente();
                    break;
                case "2":
                    ContaPoupanca cp = new ContaPoupanca();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }while(encerrar == 1);

    }
}