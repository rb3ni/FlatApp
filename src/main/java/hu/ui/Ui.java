package hu.ui;

import java.util.Scanner;

public class Ui {

    Scanner scanner = new Scanner(System.in);

    public String askTextFromUser() {
        return scanner.nextLine();
    }

    public int askIntFromUser() {
        int userInt = scanner.nextInt();
        scanner.nextLine();
        return userInt;
    }

    public double askdoubleFromUser() {
        double userInt = scanner.nextDouble();
        scanner.nextLine();
        return userInt;
    }

}
