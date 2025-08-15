package br.com.alx.model;

import lombok.Getter;

import java.util.List;

import static br.com.alx.model.BankService.ACCOUNT;

@Getter
public class AccountWallet extends Wallet {

    private final List<String> pix;

    public AccountWallet(final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
    }

    public AccountWallet(final long amont, final List<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amont, "Valor de criação da conta");
    }

    public void addMoney(final long amount, final String description) {
        var money = generateMoney(amount, description);
        this.money.addAll(money);
    }
}


