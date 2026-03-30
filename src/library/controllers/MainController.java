package library.controllers;

import java.util.Scanner;

public class MainController {
        Scanner scanner = new Scanner(System.in);
        BookController bookController = new BookController();

    public void memberMainMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Welcome ----");
            System.out.println("1. Books");
            System.out.println("2. Loans");
            System.out.println("3. My profile");
            System.out.println("4. Authors? ");
            System.out.println("0. Log out");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    bookController.memberBookMenu();
                    break;
                }
                case 2: {
                    //memberLoanMenu();
                    break;
                }
                case 3: {
                    //memberProfileMenu();
                    break;
                }
                case 4: {
                    // memberAuthorMenu();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }

    public void adminMainMenu() {
        boolean active = true;
        while (active) {
            System.out.println("---- Welcome ----");
            System.out.println("1. Books");
            System.out.println("2. Loans");
            System.out.println("3. Members");
            System.out.println("4. Authors? ");
            System.out.println("0. Log out");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    bookController.adminBookMenu();
                    break;
                }
                case 2: {
                    //adminLoanMenu();
                    break;
                }
                case 3: {
                    //adminMemberMenu();
                    break;
                }
                case 4: {
                    //adminAuthorMenu();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }
}
