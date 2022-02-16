package hu;

import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;
import hu.domain.space.SpaceMod;
import hu.domain.space.SpaceType;
import hu.repository.*;

import java.util.List;

public class InitFlatApp {
    public static void main(String[] args) {
        TransactionRepository transactionRepository = new TransactionRepository();
        AccountRepository accountRepository = new AccountRepository();
        SpaceRepository spaceRepository = new SpaceRepository();
        SpaceTypeRepository spaceTypeRepository = new SpaceTypeRepository();
        SpaceRepositoryMod spaceRepositoryMod = new SpaceRepositoryMod();
        PropertyTable propertyTable = new PropertyTable();

        accountRepository.createAccountTable();
        transactionRepository.createTransactionTable();
        spaceTypeRepository.createSpaceTypeRepository();
        spaceRepositoryMod.createSpaceTable();
        propertyTable.createPropertyTable();


        Habitant habitant;
        ExternalService externalService;
        SpaceType spaceType;
        SpaceMod space;

        List<Integer> emptyIdList = null;

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

        spaceType = new SpaceType("SpaceTypeA",
                2,
                53,
                true,
                30000,
                "Két szobás, erkéllyel rendelkező lakás, belső elhelyezkedésű.",
                1);

        space = new SpaceMod(1, 6, null, "SpaceTypeA", 1);

        //spaceTypeRepository.createNewSpaceType(spaceType);
        //spaceRepositoryMod.createNewSpace(space);

        //transactionRepository.readTransactions("src/main/resources/Transactions22_02.csv");

        propertyTable.assignHabitantAndSpace(11111,2);

    }

}
