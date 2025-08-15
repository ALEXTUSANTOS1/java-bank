package br.com.alx.repository;

import br.com.alx.exception.NoFundsEnoughException;
import br.com.alx.model.AccountWallet;
import br.com.alx.model.Money;
import br.com.alx.model.MoneyAudit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.alx.model.BankService.ACCOUNT;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE )
public final class CommonsRepository {

    public static void checkFundsForTransaction(final AccountWallet source, final long amount){
        if(source.getFunds() < amount){
            throw new NoFundsEnoughException("Sua conta nao possui fundos suficientes para realizar essa transação. " +
                    "Saldo atual: " + source.getFunds() + ", valor da transação: " + amount);
        }
    }

    public static List<Money> generateMoney(final UUID transactionId, final long funds, final String description){
        var history = new MoneyAudit(transactionId, ACCOUNT, description, OffsetDateTime.now());
        return Stream.generate(()-> new Money(history)).limit(funds).toList();
    }
}
