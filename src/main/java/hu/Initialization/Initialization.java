package hu.Initialization;

import hu.domain.Block;
import hu.domain.account.Account;
import hu.domain.account.Habitant;
import hu.domain.space.Space;
import hu.domain.space.SpaceType;
import hu.repository.BlockRepository;
import hu.repository.PropertyTableRepository;
import hu.repository.SpaceRepositories.SpaceRepository;
import hu.repository.SpaceRepositories.SpaceTypeRepository;
import hu.repository.TransactionRepository;
import hu.repository.accountRepositories.AccountRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Initialization {
    Random random = new Random();

    public static void main(String[] args) {

        TransactionRepository transactionRepository = new TransactionRepository();
        AccountRepository accountRepository = new AccountRepository();
        SpaceRepository spaceRepository = new SpaceRepository();
        SpaceTypeRepository spaceTypeRepository = new SpaceTypeRepository();
        PropertyTableRepository propertyTableRepository = new PropertyTableRepository();
        BlockRepository blockRepository = new BlockRepository();
        Initialization initialization = new Initialization();

        accountRepository.createAccountTable();
        transactionRepository.createTransactionTable();
        spaceTypeRepository.createSpaceTypeRepository();
        spaceRepository.createSpaceTable();
        propertyTableRepository.createPropertyTable();
        blockRepository.createBlockTable();


        for (int i = 0; i <= 85; i++) {
            accountRepository.createNewAccount(initialization.newHabitant());
        }
        initialization.setSpaces();
        initialization.assignSpaces();
        initialization.writeTransactions("src/main/resources/transactions/2022_01.csv");
        initialization.writeTransactions("src/main/resources/transactions/2022_02.csv");


        Block block;
        String deadLineS = "2022-01-15";
        String startingDateS = "2022-01-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deadlineLocal = LocalDate.parse(deadLineS, formatter);
        LocalDate startingDateLocal = LocalDate.parse(startingDateS, formatter);
        Date deadline = java.sql.Date.valueOf(deadlineLocal);
        Date startingDate = java.sql.Date.valueOf(startingDateLocal);
        block = new Block(1, "Macondo", 4321, "Main street", 1, "", 86, 9, null, null, deadline, startingDate);
        blockRepository.createNewBlock(block);
        //block.updateTransactions("src/main/resources/transactions/2022_01.csv");

    }


    public Habitant newHabitant() {


        final Path firstnamePath = Path.of("src/main/resources/initNames/firstNamesES.csv");
        final Path surnamePath = Path.of("src/main/resources/initNames/surnamesEs.csv");
        final Path occupationPath = Path.of("src/main/resources/initNames/occupations.csv");

        int numberOfFirstnames = listLength(firstnamePath);
        int numberOfSurnames = listLength(surnamePath);
        int numberOfOccupations = listLength(occupationPath);

        int chosenFirstname = random.nextInt(numberOfFirstnames);
        int chosenSurname = random.nextInt(numberOfSurnames);
        int chosenOccupation = random.nextInt(numberOfOccupations);

        String firstname = nameSelector(firstnamePath, chosenFirstname);
        String surname = nameSelector(surnamePath, chosenSurname);
        String occupation = nameSelector(occupationPath, chosenOccupation);

        String name = surname + " " + firstname;
        int phoneNumber = random.nextInt(900000) + 55000000;
        String email = surname.replaceAll(" ", "") + "." + firstname.charAt(0) + "@gmail.com";
        int age = random.nextInt(90) + 18;

        return new Habitant(name, phoneNumber, email.toLowerCase(Locale.ROOT), "", 0, age, occupation);

    }

    private int listLength(Path path) {
        int count = 0;
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            while ((bufferedReader.readLine()) != null) {
                count++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private String nameSelector(Path path, int selectedNumber) {
        String theName = "";
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            for (int i = 0; i < selectedNumber; i++) {
                bufferedReader.readLine();
            }
            theName = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return theName;
    }

    private void setSpaces() {
        SpaceRepository spaceRepository = new SpaceRepository();
        SpaceTypeRepository spaceTypeRepository = new SpaceTypeRepository();

        SpaceType spaceType;
        SpaceType spaceType2;
        SpaceType spaceType3;
        SpaceType spaceType4;
        SpaceType spaceType5;
        Space space;

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

        spaceTypeRepository.createNewSpaceType(spaceType);
        spaceTypeRepository.createNewSpaceType(spaceType2);
        spaceTypeRepository.createNewSpaceType(spaceType3);
        spaceTypeRepository.createNewSpaceType(spaceType4);
        spaceTypeRepository.createNewSpaceType(spaceType5);


        String chosenSpaceType;
        int doors = 1;
        for (int i = 1; i <= 9; i++) {          //emeletek
            for (int j = 1; j <= 6; j++) {      //doors
                if (j == 1 || j == 6) {
                    chosenSpaceType = "spaceTypeC";
                } else if (j == 2 || j == 5) {
                    chosenSpaceType = "spaceTypeB";
                } else {
                    chosenSpaceType = "spaceTypeA";
                }

                space = new Space(i, doors, null, chosenSpaceType, 1, 0);
                spaceRepository.createNewSpace(space);
                doors++;
            }
        }

        space = new Space(0, doors, null, "businessSpace", 1, 0);
        doors++;
        spaceRepository.createNewSpace(space);

        for (int i = 1; i <= 3; i++) {
            space = new Space(-1, doors, null, "parkingSlot", 1, 0);
            doors++;
            spaceRepository.createNewSpace(space);
        }


    }

    private void assignSpaces() {
        Random random = new Random();
        PropertyTableRepository propertyTableRepository = new PropertyTableRepository();
        AccountRepository accountRepository = new AccountRepository();

        List<Integer> habitantIds = new ArrayList<>();
        // 86 db habitant, 0----->85
        // 58 db spaces, 0--->57
        habitantIds = accountRepository.accountIdList();

        for (int i = 1; i <= 86; i++) {
            if (i <= 58) {
                propertyTableRepository.assignHabitantAndSpace(habitantIds.get(i-1), i);
            } else {
                propertyTableRepository.assignHabitantAndSpace(habitantIds.get(i-1), (random.nextInt(55) + 1));
            }
        }

    }

    private void writeTransactions(String path) {
        AccountRepository accountRepository = new AccountRepository();

        //src/main/resources/transactions/2022_01.csv
        Path output = Path.of(path);
        List<Integer> habitantIds = accountRepository.accountIdList();


        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(output, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            bufferedWriter.write("card_number,name,date,amount,description,transaction_number");
            bufferedWriter.newLine();

            for (Integer habitantId : habitantIds) {
                if (random.nextInt(100) < 95) {
                    bufferedWriter.write(cardNumberGenerator() + "," +
                            accountRepository.searchAccountById(habitantId).getName() + "," +
                            dateGenerator() + "," +
                            amountGenerator() + "," +
                            descriptionGenerator(habitantId) + "," +
                            transactionNumberGenerator());
                    bufferedWriter.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
// 1234567889,Bruce Wayne,2022-01-15 16:00:00,50000,Közös költség 111111,TR9876FSAF


    int cardNumber = 0;
    String name = "";
    String date = "";
    int amount = 0;
    String description = "";
    String transactionNumber;

    public int cardNumberGenerator() {
        return (random.nextInt(899999999) + 100000000);
    }

    public String dateGenerator() {

        String date = "2022-01-";

        int day = random.nextInt(29) + 1;

        if (day < 10) {
            date += "0" + day;
        } else {
            date += day;
        }

        int hours = random.nextInt(24);
        int minutes = random.nextInt(60);
        int seconds = random.nextInt(60);

        if (hours < 10) {
            date += " 0" + hours + ":";
        } else {
            date += " " + hours + ":";
        }

        if (minutes < 10) {
            date += "0" + minutes + ":";
        } else {
            date += minutes + ":";
        }

        if (seconds < 10) {
            date += "0" + seconds;
        } else {
            date += seconds;
        }


        //2022-01-15 16:00:00

        return date;
    }

    public String descriptionGenerator(int habitantId) {
        String desc = "";
        int propability = random.nextInt(10);
        if (propability > 2 && propability < 7) {
            desc = "" + habitantId;
        } else if (propability >= 7) {
            desc = "Cost " + habitantId;
        }
        return desc;
    }

    public String transactionNumberGenerator() {
        String tNr = "";
        String alphabet = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i <= 10; i++) {
            tNr += alphabet.charAt(random.nextInt(alphabet.length()));
        }
        return tNr;
    }

    public int amountGenerator(){
        return (random.nextInt(5) * 10000) + 10000;
    }

}

