package br.com.alx.repository;

import br.com.alx.exception.AccountNotFoundException;
import br.com.alx.exception.PixInUseException;
import br.com.alx.model.AccountWallet;
import br.com.alx.model.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static br.com.alx.repository.CommonsRepository.checkFundsForTransaction;

public class AccountRepository {

    private final List<AccountWallet> accounts = new ArrayList<>();

    public AccountWallet create(final List<String> pix, final long initialFunds){
        if(!accounts.isEmpty()) {
            var pixInUse = accounts.stream().flatMap(a -> a.getPix().stream()).toList();
            for (var p : pix) {
                if (pixInUse.contains(p)) {
                    throw new PixInUseException("O PIX '" + p + "' já está em uso.");
                }
            }
        }
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount){
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "Depósito via PIX");
    }

    public long withdraw(final String pix, final long amount){
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return  amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount){
        var source = findByPix(sourcePix);
        checkFundsForTransaction(source, amount);
        var target = findByPix(targetPix);
        var message = "pix enviado de '" + sourcePix + "' para '" + targetPix + " '";
        target.addMoney(source.reduceMoney(amount), source.getService(), message);
    }

    public AccountWallet findByPix(final String pix){
        return accounts.stream()
                .filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(()-> new AccountNotFoundException("A conta com o PIX " + pix + " não foi encontrada."));
    }

    public List<AccountWallet> list(){
        return this.accounts;
    }

    public Map<LocalDateTime, List<Transaction>> getHistory(String pix) throws AccountNotFoundException {
        AccountWallet wallet = findByPix(pix);
        if (wallet == null) {
            throw new AccountNotFoundException("Conta não encontrada para a chave pix: " + pix);
        }
        return new LinkedHashMap<>();
    }
}
