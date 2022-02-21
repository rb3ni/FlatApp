package hu;

import hu.domain.Block;
import hu.domain.account.Account;
import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;
import hu.domain.event.Complain;
import hu.domain.event.Emergency;
import hu.domain.event.Event;
import hu.domain.event.Reminder;
import hu.domain.space.Space;
import hu.repository.BlockRepository;
import hu.repository.EventRepositories.EventRepository;
import hu.repository.EventRepositories.EventTableRepository;
import hu.repository.PropertyTableRepository;
import hu.repository.SpaceRepositories.SpaceRepository;
import hu.repository.SpaceRepositories.SpaceTypeRepository;
import hu.repository.TransactionRepository;
import hu.repository.accountRepositories.AccountRepository;
import hu.ui.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlatAppMenu {

    AccountRepository aR = new AccountRepository();
    BlockRepository bR = new BlockRepository();
    EventRepository eR = new EventRepository();
    EventTableRepository eTR = new EventTableRepository();
    PropertyTableRepository pTR = new PropertyTableRepository();
    SpaceRepository sR = new SpaceRepository();
    SpaceTypeRepository sTR = new SpaceTypeRepository();
    TransactionRepository tR = new TransactionRepository();
    Ui ui = new Ui();

    public void printFlatAppSystemMenu() {
        System.out.println("Press number for choose between options");
        System.out.println("1: Upload transactions from csv file");
        System.out.println("2: Check debts");
        System.out.println("3: Check balance");
        System.out.println("4: Search Accounts by Space");
        System.out.println("5: Search Spaces by Account");
        System.out.println("6: List all external services");
        System.out.println("7: Create new Account and save to database");
        System.out.println("8: Create new Event");
        System.out.println("9: Quit");
    }

    public void startApplication() {

        boolean flag = true;
        aR.createAccountTable();
        eR.createEventTable();
        eTR.createEventConnectionTable();
        pTR.createPropertyTable();
        sR.createSpaceTable();
        ;
        sTR.createSpaceTypeRepository();
        tR.createTransactionTable();

        printFlatAppSystemMenu();
        while (flag) {
            int userInput = ui.askIntFromUser();
            switch (userInput) {
                case 1:
                    System.out.print("Give the path of the file: ");
                    String path = ui.askTextFromUser();

                    Block block = bR.searchBlockById(1);
                    block.updateTransactions(path);
                    break;
                case 2:
                    System.out.print("The following spaces have debt:");
                    System.out.println();
                    tR.checkDebts();

                    break;
                case 3:
                    System.out.print("Give space's floor: ");
                    int floorBalance = ui.askIntFromUser();
                    System.out.print("Give space's door number: ");
                    int doorBalance = ui.askIntFromUser();

                    Space space = sR.searchSpacesByFloorAndDoor(floorBalance, doorBalance);
                    System.out.print("Balance: " + space.getBalance());
                    break;
                case 4:
                    System.out.print("Give space's floor: ");
                    int floor = ui.askIntFromUser();
                    System.out.print("Give space's door number: ");
                    int door = ui.askIntFromUser();

                    List<Account> accounts = aR.searchAccountsBySpace(floor, door);
                    System.out.println(accounts);
                    break;
                case 5:
                    System.out.print("Give Account's name: ");
                    String nameForSpaces = ui.askTextFromUser();
                    System.out.print("Give Account's email: ");
                    String emailForSpaces = ui.askTextFromUser();

                    List<Space> spaces = sR.searchSpacesByAccountNameAndEmail(nameForSpaces, emailForSpaces);
                    System.out.println(spaces);
                    break;
                case 6:
                    List<ExternalService> externalServices = aR.listAllExternalService();
                    System.out.println(externalServices);
                    break;
                case 7:
                    createNewAccountSubMenu();
                    System.out.println("Account saved");
                    break;
                case 8:
                    createNewEventSubMenu();
                    break;
                case 9:
                    flag = false;
            }
        }
    }

    private void createNewEventSubMenu() {
        System.out.println("Press number for choose between options");
        System.out.println("1: Create new Complain");
        System.out.println("2: Create new Emergency");
        System.out.println("3: Create new Reminder");
        System.out.println("4: Create new other Event");
        int userInput = ui.askIntFromUser();

        List<String> eventDatas = askEventDatas();
        List<java.sql.Date> dates = getEventDates(eventDatas.get(2));

        switch (userInput) {
            case 1:
                System.out.print("Give your email: ");
                String senderComplainEmail = ui.askTextFromUser();
                Account senderComplain = aR.searchAccountByEmail(senderComplainEmail);

                System.out.print("Send complain to: (Example: email1, email2, email3) ");
                String receiversEmail = ui.askTextFromUser();
                List<Account> receivers = getReceivers(receiversEmail);

                eR.createNewEvent(new Complain(eventDatas.get(0), eventDatas.get(1), dates.get(0),
                        dates.get(1), senderComplain, receivers));
                break;
            case 2:
                System.out.print("Give your email: ");
                String senderEmergencyEmail = ui.askTextFromUser();
                Account senderEmergency = aR.searchAccountByEmail(senderEmergencyEmail);

                List<Space> affectedSpaces = giveAffectedSpaces(userInput);

                eR.createNewEvent(new Emergency(eventDatas.get(0), eventDatas.get(1), dates.get(0),
                        dates.get(1), senderEmergency, affectedSpaces));
                break;
            case 3:
                List<Space> affectedSpacesReminder = giveAffectedSpaces(userInput);

                eR.createNewEvent(new Reminder(eventDatas.get(0), eventDatas.get(1), dates.get(0),
                        dates.get(1), affectedSpacesReminder));
                break;
            case 4:
                eR.createNewEvent(new Event(eventDatas.get(0), eventDatas.get(1), dates.get(0),
                        dates.get(1)));
        }
    }

    private List<Space> giveAffectedSpaces(int previousUserInput) {
        boolean flag = true;
        List<Space> affectedSpaces = new ArrayList<>();

        while (flag) {

            if (previousUserInput == 2) {
                System.out.println("Press number for choose between options");
                System.out.println("1: Add Space to Emergency");
                System.out.println("2: Send Emergency");
            } else {
                System.out.println("Press number for choose between options");
                System.out.println("1: Add Space to Reminder");
                System.out.println("2: Send Reminder");
            }

            int userInput = ui.askIntFromUser();

            switch (userInput) {
                case 1:
                    System.out.print("Give affected space's floor: ");
                    int floor = ui.askIntFromUser();
                    System.out.print("Give affected space's door number: ");
                    int door = ui.askIntFromUser();

                    Space affectedSpace = sR.searchSpacesByFloorAndDoor(floor, door);
                    affectedSpaces.add(affectedSpace);
                    break;
                case 2:
                    flag = false;
                    break;
            }
        }
        return affectedSpaces;
    }

    private List<Account> getReceivers(String receiversEmail) {
        List<Account> receivers = new ArrayList<>();

        String[] emails = receiversEmail.split(", ");
        for (int i = 0; i < emails.length; i++) {
            Account receiver = aR.searchAccountByEmail(emails[i]);
            receivers.add(receiver);
        }
        return receivers;
    }

    private List<java.sql.Date> getEventDates(String eventDate) {
        List<java.sql.Date> dates = new ArrayList<>();

        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate eventDateLocal = LocalDate.parse(eventDate, formatter);
        java.sql.Date eventDateSql = java.sql.Date.valueOf(eventDateLocal);

        dates.add(sqlDate);
        dates.add(eventDateSql);

        return dates;
    }

    private List<String> askEventDatas() {
        List<String> eventDatas = new ArrayList<>();

        System.out.print("Give the name of the event: ");
        String name = ui.askTextFromUser();
        System.out.print("Give a description: ");
        String description = ui.askTextFromUser();
        System.out.print("Give the date of the event: ");
        String eventDate = ui.askTextFromUser();

        eventDatas.add(name);
        eventDatas.add(description);
        eventDatas.add(eventDate);

        return eventDatas;
    }

    private void createNewAccountSubMenu() {
        System.out.println("Press number for choose between options");
        System.out.println("1: Create new Habitant");
        System.out.println("2: Create new External Service");
        int userInput = ui.askIntFromUser();

        List<String> accountData = askAccountDatas();

        switch (userInput) {
            case 1:
                System.out.print("Give age: ");
                int age = ui.askIntFromUser();
                System.out.print("Give occupation: ");
                String occupation = ui.askTextFromUser();

                aR.createNewAccount(new Habitant(accountData.get(0), Integer.parseInt(accountData.get(1)),
                        accountData.get(2), accountData.get(3), 0, age, occupation));
                break;
            case 2:
                System.out.print("Give companyName: ");
                String companyName = ui.askTextFromUser();

                aR.createNewAccount(new ExternalService(accountData.get(0), Integer.parseInt(accountData.get(1)),
                        accountData.get(2), accountData.get(3), 0, companyName));
                break;
        }
    }

    private List<String> askAccountDatas() {
        List<String> accountDatas = new ArrayList<>();
        System.out.print("Give name: ");
        String name = ui.askTextFromUser();
        System.out.print("Give phone number: ");
        int phoneNumber = ui.askIntFromUser();
        System.out.print("Give email: ");
        String email = ui.askTextFromUser();
        System.out.print("Give responsibility: ");
        String responsibility = ui.askTextFromUser();

        accountDatas.add(name);
        accountDatas.add(Integer.toString(phoneNumber));
        accountDatas.add(email);
        accountDatas.add(responsibility);

        return accountDatas;
    }
}
