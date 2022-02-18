package hu;

import hu.domain.Block;
import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;
import hu.domain.space.Space;
import hu.domain.space.SpaceType;
import hu.repository.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InitFlatApp {

    public static void main(String[] args) {
        TransactionRepository transactionRepository = new TransactionRepository();
        AccountRepository accountRepository = new AccountRepository();
        SpaceRepository spaceRepository = new SpaceRepository();
        SpaceTypeRepository spaceTypeRepository = new SpaceTypeRepository();
        PropertyTableRepository propertyTableRepository = new PropertyTableRepository();
        BlockRepository blockRepository = new BlockRepository();

        accountRepository.createAccountTable();
        transactionRepository.createTransactionTable();
        spaceTypeRepository.createSpaceTypeRepository();
        spaceRepository.createSpaceTable();
        propertyTableRepository.createPropertyTable();
        blockRepository.createBlockTable();


        Habitant habitant;
        ExternalService externalService;
        SpaceType spaceType;
        SpaceType spaceType1;
        SpaceType spaceType2;
        SpaceType spaceType3;
        SpaceType spaceType4;
        SpaceType spaceType5;
        Space space;
        Space space2;
        Space space3;
        Space space4;
        Space space5;
        Block block;

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


        System.out.println(accountRepository.createNewAccount(habitant));
        System.out.println(accountRepository.createNewAccount(externalService));
        System.out.println(accountRepository.searchAccountById(1));
        System.out.println(accountRepository.searchAccountById(2));
        System.out.println(accountRepository.accountIdList());
        accountRepository.overwriteAccountIdByName("Bruce Wayne", 111111);

        spaceType = new SpaceType("spaceTypeA",
                2,
                53,
                true,
                30000,
                "Két szobás, erkéllyel rendelkező lakás, belső elhelyezkedésű.",
                1);

        spaceType2 = new SpaceType("spaceTypeB",
                2,
                51,
                false,
                30000,
                "Két szobás lakás, belső elhelyezkedésű.",
                1);

        spaceType3 = new SpaceType("spaceTypeC",
                1.5,
                45,
                false,
                30000,
                "Másfél szobás lakás, külső elhelyezkedésű.",
                1);

        spaceType4 = new SpaceType("parkingSlot",
                0,
                6,
                false,
                10000,
                "Parkolóhely",
                1);

        spaceType5 = new SpaceType("businessSpace",
                4,
                96,
                false,
                60000,
                "Földszinti üzlethelyiség",
                1);

        space = new Space(1, 7, null, "spaceTypeA", 1, 0);
        space2 = new Space(1, 8, null, "spaceTypeB", 1, 0);
        space3 = new Space(1, 9, null, "spaceTypeC", 1, 0);
        space4 = new Space(-1, 0, null, "parkingSlot", 1, 0);
        space5 = new Space(0, 1, null, "businessSpace", 1, 0);

        System.out.println(spaceTypeRepository.createNewSpaceType(spaceType));
        System.out.println(spaceTypeRepository.createNewSpaceType(spaceType2));
        System.out.println(spaceTypeRepository.createNewSpaceType(spaceType3));
        System.out.println(spaceTypeRepository.createNewSpaceType(spaceType4));
        System.out.println(spaceTypeRepository.createNewSpaceType(spaceType5));
        spaceRepository.createNewSpace(space);
        spaceRepository.createNewSpace(space2);
        spaceRepository.createNewSpace(space3);
        spaceRepository.createNewSpace(space4);
        spaceRepository.createNewSpace(space5);


        String deadLineS = "2022-01-15";
        String startingDateS = "2022-01-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadlineLocal = LocalDate.parse(deadLineS, formatter);
        LocalDate startingDateLocal = LocalDate.parse(startingDateS, formatter);
        Date deadline = java.sql.Date.valueOf(deadlineLocal);
        Date startingDate = java.sql.Date.valueOf(startingDateLocal);

        block = new Block(1, "Macondo", 4321, "Fő út", 1, "", 40, 5, null, null, deadline, startingDate);

        blockRepository.createNewBlock(block);

        transactionRepository.readTransactions("src/main/resources/Transactions22_02.csv");
        System.out.println(transactionRepository.unassignedTransactions());

        propertyTableRepository.assignHabitantAndSpace(111111, 1);
        propertyTableRepository.assignHabitantAndSpace(111111, 2);
        System.out.println(propertyTableRepository.searchSpacesByHabitantId(111111));

        block.updateTransactions();

    }


}


