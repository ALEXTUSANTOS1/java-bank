package br.com.alx;

import br.com.alx.exception.AccountNotFoundException;
import br.com.alx.exception.NoFundsEnoughException;
import br.com.alx.exception.WalletNotFoundException;
import br.com.alx.model.AccountWallet;
import br.com.alx.repository.AccountRepository;
import br.com.alx.repository.InvestmentRepository;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

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
            System.out.println("3 - Criar uma carteira de investimento");
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
                case 1 -> criarConta();
                case 2 -> criarInvestimento();
                case 3 -> carteiraDeInvestimento();
                case 4 -> depositar();
                case 5 -> sacar();
                case 6 -> tranferencia();
                case 7 -> investimento();
                case 8 -> sacarInvestimento();
                case 9 -> accountRepository.list().forEach(System.out::println);
                case 10 -> investmentRepository.list().forEach(System.out::println);
                case 11 -> investmentRepository.listWallets().forEach(System.out::println);
                case 12 -> {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos atualizados com sucesso!");
                    continue;
                }
                case 13 -> history();
                case 14 -> System.exit(0);
                default ->
                    System.out.println("Opção inválida, tente novamente.");

            }
        }
    }

    private static void history() {
        System.out.println("Informe a chave pix para ver o histórico:");
        var pix = sc.next();
        AccountWallet wallet;
        try{
            var sortedHistory = accountRepository.getHistory(pix);
            sortedHistory.forEach((k,v)->{
                System.out.println(k.format(ISO_DATE_TIME));
                System.out.println(v.getFirst().transactioId());
                System.out.println(v.getFirst().description());
                System.out.println("R$" + (v.size() / 100.0) + " , " + (v.size() % 100));
            });
        }catch (AccountNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private static void sacarInvestimento() {
        System.out.println("informe a chave pix da conta para resgatar o investimento");
        var pix = sc.next();
        System.out.println("informe o valor a ser resgatado");
        var amount = sc.nextLong();
        try{
            investmentRepository.withdraw(pix, amount);
        }catch (NoFundsEnoughException | AccountNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private static void investimento() {
        System.out.println("informe a chave pix da conta para investir");
        var pix = sc.next();
        System.out.println("Informe o valor a ser investido");
        var amount = sc.nextLong();
        try{
            investmentRepository.deposit(pix, amount);
        }catch (AccountNotFoundException | WalletNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private static void carteiraDeInvestimento() {
        System.out.println("Informe a chave pix da conta");
        var pix = sc.next();
        var accont = accountRepository.findByPix(pix);
        System.out.println("Informe o id do investimento");
        var investmentId = sc.nextInt();
        var investmetWallet = investmentRepository.initInvestment(accont, investmentId);
        System.out.println("Carteira de investimento criada com sucesso! " + investmetWallet);
    }

    private static void tranferencia() {
        System.out.println("Informe a chave pix da conta de origem:");
        var source = sc.next();
        System.out.println("Informe a chave pix da conta de destino:");
        var target = sc.next();
        System.out.println("Informe o valor a ser transferido:");
        var amount = sc.nextLong();
        try {
            accountRepository.transferMoney(source, target, amount);
        }catch (AccountNotFoundException e){
            System.out.println(e.getMessage());
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
        var pix = Arrays.stream(sc.nextLine().split(";")).toList();
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
