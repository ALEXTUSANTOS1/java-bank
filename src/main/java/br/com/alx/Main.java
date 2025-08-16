package br.com.alx;

import br.com.alx.exception.AccountNotFoundException;
import br.com.alx.exception.NoFundsEnoughException;
import br.com.alx.repository.AccountRepository;
import br.com.alx.repository.InvestmentRepository;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Olá, seja bem-vindo ao sistema de investimentos!");

        while (true) {
            System.out.println("Selecione a opção desejada");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Criar investimento");
            System.out.println("3 - Fazer investimento");
            System.out.println("4 - Depositar");
            System.out.println("5 - Sacar");
            System.out.println("6 - Transferencia entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Sacar investimento");
            System.out.println("9 - Listar contas");
            System.out.println("10 - Listar investimentos");
            System.out.println("11 - Listar carteiras de investimento");
            System.out.println("12 - Atualizar Investimentos");
            System.out.println("13 - Historico da conta");
            System.out.println("14 - Sair");

            var option = sc.nextInt();

            switch (option) {
                case 1: criarConta();
                case 2: criarInvestimento();
                case 4: depositar();
                case 5: sacar();
                case 9:accountRepository.list().forEach(System.out::println);
                case 10:investmentRepository.list().forEach(System.out::println);
                case 11:investmentRepository.listWallets().forEach(System.out::println);
                case 12: {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos atualizados com sucesso!");
                    continue;
                }
                case 14 : System.exit(0);
                default:
                    System.out.println("Opção inválida, tente novamente.");
                    continue;

            }
        }
    }

    private static void sacar() {
        System.out.println("Informe a chave pix da conta que deseja sacar:");
        var pix = sc.next();
        System.out.println("Informe o valor a ser sacado:");
        var amount = sc.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
        }catch (NoFundsEnoughException | AccountNotFoundException e) {
            System.out.println("Erro ao sacar: " + e.getMessage());
        }
    }

    private static void criarInvestimento() {
        System.out.println("Informe a taxa do investimento");
        var tax = sc.nextLong();
        System.out.println("Informe o valor do investimento");
        var initialAmount = sc.nextLong();
        var investment = investmentRepository.create(tax, initialAmount);
        System.out.println("Investimento criado com sucesso! " + investment);
    }

    private static void criarConta() {
        System.out.println("Informe as chaves pix separadas por ponto e vírgula (exemplo: chave1;chave2):");
        var pix = Arrays.stream(sc.nextLine().split(",")).toList();
        System.out.println("Informe o valor inicial de deposito:");
        var amount = sc.nextLong();
        var wallet = accountRepository.create(pix, amount);
        System.out.println("Conta criada com sucesso!" + wallet);
    }

    private static void depositar(){
        System.out.println("Informe a chave pix da conta que deseja depositar:");
        var pix = sc.nextLine();
        System.out.println("Informe o valor a ser depositado:");
        var amount = sc.nextLong();
        accountRepository.deposit(pix, amount);
    }
}
