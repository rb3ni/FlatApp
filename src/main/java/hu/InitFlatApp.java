package hu;

import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;
import hu.domain.space.Space;
import hu.repository.AccountRepository;

public class InitFlatApp {
    public static void main(String[] args) {
    AccountRepository accountRepository = new AccountRepository();
    accountRepository.createAccountTable();

        Habitant habitant;
        ExternalService externalService;
        Space space;

        habitant = new Habitant(1,
                "Bruce Wayne",
                5551234,
                "bruce_wayne@gothamail.com",
                "Közös képviselő",
                30000,
                32,
                "CEO a Wayne vállalatnál");

        externalService = new ExternalService(2,
                "Pablo Escobar",
                5554321,
                "escobar@gmail.com",
                "Kertész",
                50000,
                "Escobar&Escobar kft.");

        //System.out.println(accountRepository.createNewAccount(habitant));
        //System.out.println(accountRepository.createNewAccount(externalService));
        System.out.println(accountRepository.searchAccountById(1));
        System.out.println(accountRepository.searchAccountById(2));

    }

}
