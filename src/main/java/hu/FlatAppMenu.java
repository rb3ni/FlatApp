package hu;

import hu.domain.account.Account;
import hu.domain.account.ExternalService;
import hu.domain.account.Habitant;
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
        System.out.println("1: Upload transactions from csv file"); // String "Path" submenu: check trans(Unassigned) és reminder kiküldés??
        System.out.println("2: Check debts"); // List<Account>
        System.out.println("3: Check balance"); //Account(emailel)
        System.out.println("4: Search Accounts by Space"); // List<Account> (floor, door)
        System.out.println("5: Search Spaces by Account"); // List<space> (név, email)
        System.out.println("6: List all external services"); // List<ExternalServices>
        System.out.println("7: Create new Account and save to database"); // submenu external or habitant
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
                    System.out.print("Please give the path of the file: ");
                    String path = ui.askTextFromUser();

                    tR.readTransactions(path);
                    // TODO Innen egyelőre nem látom még hogyan tovább.
                    uploadTransactionSubMenu(); // van erre szükség egyáltalán?
                    // List<Account> misssingAccounts = checkMissingAccountsFromTransactions();
                    // addAccountManuallyToAccounts();
                    break;
                case 2:
                    // TODO transaction method for this?

                    break;
                case 3:
                    System.out.print("Give Account email: ");
                    String email = ui.askTextFromUser();
                    System.out.print(aR.searchAccountByEmail(email));
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
                    System.out.println();
                    break;
                case 8:

                    break;
                case 9:
                    flag = false;
            }
        }
    }


    private void createNewAccountSubMenu() {
        System.out.println("Press number for choose between options");
        System.out.println("1: Create new Habitant");
        System.out.println("2: Create new External Service");
        int userInput = ui.askIntFromUser();

        switch (userInput) {
            case 1:
                System.out.print("Give name: ");
                String name = ui.askTextFromUser();
                System.out.print("Give phone number: ");
                int phoneNumber = ui.askIntFromUser();
                System.out.print("Give email: ");
                String email = ui.askTextFromUser();
                System.out.print("Give responsibility: ");
                String responsibility = ui.askTextFromUser();
                System.out.print("Give cost: ");
                int cost = ui.askIntFromUser();
                System.out.print("Give age: ");
                int age = ui.askIntFromUser();
                System.out.print("Give occupation: ");
                String occupation = ui.askTextFromUser();
                //TODO
                aR.createNewAccount(new Habitant(name, phoneNumber, email, responsibility, cost, age, occupation));
                break;
            case 2:
                // TODO transaction method for this?
                break;
        }
    }

    private void uploadTransactionSubMenu() {
        System.out.println("Press number for choose between options");
        System.out.println("1: Check missing Accounts from Transactions"); // Nem biztos hogy ez az egész kell
        System.out.println("2: Create new External Service");
        int userInput = ui.askIntFromUser();
    }
}
