package org.pao.mani;

import org.pao.mani.commands.*;
import org.pao.mani.data.*;
import org.pao.mani.domain.*;
import org.pao.mani.queries.*;
import org.pao.mani.queries.utils.TimestampRange;

public class Main {
    public static void main(String[] args) {
        var accountRepository = new InMemoryAccountRepository();
        var transactionRepository = new InMemoryTransactionRepository();
        var cardRepository = new InMemoryCardRepository();

        var user1Id = User.Id.generate();
        var user2Id = User.Id.generate();

        var createAccount = new CreateAccount(accountRepository);
        var account1 = createAccount.run(new CreateAccount.Command(user1Id, Money.eur(1000)));
        var account2 = createAccount.run(new CreateAccount.Command(user2Id, Money.ron(0)));

        var lookupAccounts = new LookupAccounts(accountRepository);

        System.out.println(lookupAccounts.run(new LookupAccounts.Query(account1)));
        System.out.println(lookupAccounts.run(new LookupAccounts.Query(account2)));

        var accountBalance = new AccountBalance(accountRepository, transactionRepository);
        System.out.println(accountBalance.run(new AccountBalance.Query(account1, TimestampRange.empty())));

        var createTransaction = new CreateTransaction(accountRepository, transactionRepository);
        var transaction1 = createTransaction.run(new CreateTransaction.Command(account1, account2, Money.eur(1000)));

        var lookupTransaction = new LookupTransactions(transactionRepository);
        System.out.println(lookupTransaction.run(new LookupTransactions.Query(transaction1)));

        var getTransactions = new GetTransactions(transactionRepository);
        System.out.println(getTransactions.run(new GetTransactions.Query(account1, TimestampRange.empty(), 20)));

        var tr2 = createTransaction.run(new CreateTransaction.Command(account2, account1, Money.eur(500)));
        System.out.println(accountBalance.run(new AccountBalance.Query(account1, TimestampRange.empty())));
        System.out.println(accountBalance.run(new AccountBalance.Query(account2, TimestampRange.empty())));

        var addCard = new AddCard(cardRepository);
        var card1 = addCard.run(new AddCard.Command(account1, Card.Virtual.of(account1, new Iban("DE12 3456 7890 1234 5678 90"))));
        var card2 = addCard.run(new AddCard.Command(account2, Card.Physical.of(account2, new Iban("DE12 3456 7890 1234 5678 91"), new Pin("1234"))));

        var getCards = new GetCards(cardRepository);
        System.out.println(getCards.run(new GetCards.Query(account1)));
        System.out.println(getCards.run(new GetCards.Query(account2)));

        var changeCardPin = new ChangeCardPin(cardRepository);
        changeCardPin.run(new ChangeCardPin.Command(account2, new Pin("1235")));

        var revokeCard = new RevokeCard(cardRepository);
        revokeCard.run(new RevokeCard.Command(card1));

        System.out.println(getCards.run(new GetCards.Query(account1)));

        var closeAccount = new CloseAccount(accountRepository);
        closeAccount.run(new CloseAccount.Command(account1));
    }
}