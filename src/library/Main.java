package library;

import library.controllers.BookController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BookController bookcontroller = new BookController();
        boolean active = true;

            while (active) {
                System.out.println("---- Log in ----");
                System.out.println("1. Member");
                System.out.println("2. Admin");
                System.out.println("0. Exit");
                int choice = Integer.parseInt(scanner.nextLine());


//                System.out.println("---- Main Menu ----");
//                System.out.println("1. Book-menu");
//                System.out.println("2. Author-menu");
//                System.out.println("3. Loan-menu");
//                System.out.println("4. Member-menu");
//                System.out.println("0. Exit");
//                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1: {
                        bookcontroller.memberMainMenu();
                        break;
                    }
                    case 2: {
                        bookcontroller.adminBookMenu();
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
