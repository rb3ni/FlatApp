package hu.repository;

import hu.domain.account.Account;
import hu.domain.event.Complain;
import hu.domain.event.Emergency;
import hu.domain.event.Event;
import hu.domain.event.Reminder;
import hu.domain.space.Space;
import hu.repository.EventRepositories.EventRepository;
import hu.repository.EventRepositories.EventTableRepository;
import hu.repository.SpaceRepositories.SpaceRepository;
import hu.repository.accountRepositories.AccountRepository;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SandBoxBen {

    Connection connection;

    public static void main(String[] args) {
        SandBoxBen sandBoxBen = new SandBoxBen();

        EventRepository eventRepository = new EventRepository();
        EventTableRepository eventTableRepository = new EventTableRepository();
        AccountRepository accountRepository = new AccountRepository();
        SpaceRepository spaceRepository = new SpaceRepository();

        eventRepository.createEventTable();
        eventTableRepository.createEventConnectionTable();


        List<Space> spaceList = new ArrayList<>();
        String dateS = "2022-02-18";
        String eventDateS = "2022-02-28";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateLocal = LocalDate.parse(dateS, formatter);
        LocalDate eventDateLocal = LocalDate.parse(eventDateS, formatter);
        Date date = java.sql.Date.valueOf(dateLocal);
        Date eventDate = java.sql.Date.valueOf(eventDateLocal);

        Event reminder = new Reminder("Közösgyűlés", "Felújítások átbeszélése", date, eventDate, spaceList);

        String eventName = "Bulikázunk hétköznap éjszaka mi?";
        String description = "Meg lesztek villámcsapva";
        String dateSS = "2022-02-18";
        String eventDateSS = "2022-02-28";
        DateTimeFormatter formatterS = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateLocalS = LocalDate.parse(dateSS, formatterS);
        LocalDate eventDateLocalSS = LocalDate.parse(eventDateSS, formatterS);
        Date dateSSSS = java.sql.Date.valueOf(dateLocalS);
        Date eventDateSSSS = java.sql.Date.valueOf(eventDateLocalSS);

        Account account = accountRepository.searchAccountByEmail("bruce_wayne@gothamail.com");
        List<Account> receivers = new ArrayList<>();
        receivers.add(accountRepository.searchAccountByEmail("saveme@fromdeath.com"));
        receivers.add(accountRepository.searchAccountByEmail("immattdamon@damon.com"));

        Event complain = new Complain(eventName, description, dateSSSS, eventDateSSSS, account, receivers);


        String emergencyName = "Süllyed a hajó";
        String emergencyDescription = "Torpedó találat érte a házat";
        Account emergencySender = accountRepository.searchAccountByEmail("saveme@fromdeath.com");
        List<Space> emergencyAffectedSpaces = new ArrayList<>();
        Space space = spaceRepository.searchSpacesByFloorAndDoor(1, 7);
        Space space2 = spaceRepository.searchSpacesByFloorAndDoor(1, 8);
        Space space3 = spaceRepository.searchSpacesByFloorAndDoor(1, 9);

        emergencyAffectedSpaces.add(space);
        emergencyAffectedSpaces.add(space2);
        emergencyAffectedSpaces.add(space3);

        Event emergency = new Emergency(emergencyName, emergencyDescription, date, date, emergencySender, emergencyAffectedSpaces);

        String etcEvent = "Fazekas klub";
        String etcDescription = "Jók a progamok, csak sok a köcsög";

        Event event = new Event(etcEvent, etcDescription, date, dateSSSS);

        eventRepository.createNewEvent(complain);
        eventRepository.createNewEvent(reminder);
        eventRepository.createNewEvent(emergency);
        eventRepository.createNewEvent(event);
    }
}
