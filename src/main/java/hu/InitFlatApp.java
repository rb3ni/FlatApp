package hu;

import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;
import hu.domain.space.Space;
import hu.repository.AccountRepository;
import hu.repository.TransactionRepository;

public class InitFlatApp {
    public static void main(String[] args) {
        TransactionRepository transactionRepository = new TransactionRepository();
        AccountRepository accountRepository = new AccountRepository();
        accountRepository.createAccountTable();
        transactionRepository.createTransactionTable();

        Habitant habitant;
        ExternalService externalService;
        Space space;

        habitant = new Habitant(
                "Bruce Wayne",
                5551234,
                "bruce_wayne@gothamail.com",
                "Közös képviselő",
                30000,
                32,
                "CEO a Wayne vállalatnál");

        externalService = new ExternalService(
                "Pablo Escobar",
                5554321,
                "escobar@gmail.com",
                "Kertész",
                50000,
                "Escobar&Escobar kft.");

        //System.out.println(accountRepository.createNewAccount(habitant));
        //System.out.println(accountRepository.createNewAccount(externalService));
        //System.out.println(accountRepository.searchAccountById(1));
        //System.out.println(accountRepository.searchAccountById(2));
        System.out.println(accountRepository.accountIdList());
        //accountRepository.overwriteAccountIdByName("Bruce Wayne",111111);



        transactionRepository.readTransactions("src/main/resources/Transactions22_02.csv");



    }

}
