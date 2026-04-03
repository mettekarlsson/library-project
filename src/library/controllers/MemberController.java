package library.controllers;

import library.models.Member;
import library.services.MemberService;

import java.util.ArrayList;
import java.util.Scanner;

import static library.controllers.MainController.loggedInUser;

public class MemberController {
    Scanner scanner = new Scanner(System.in);
    MemberService memberService = new MemberService();

    public void memberProfileMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Your profile ----");
            System.out.println("1. Show my profile info");
            System.out.println("2. Update my info");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                    Member member = memberService.getMemberById(loggedInUser.getId());
                    System.out.println(member);
                    break;
                }
                case 2: {
                    boolean current = true;
                    while (current) {
                        System.out.println("---- Update member info ----");
                        System.out.println("1. Update first name");
                        System.out.println("2. Update last name");
                        System.out.println("3. Update email");
                        System.out.println("4. Update password");
                        System.out.println("0. Return");
                        int option = Integer.parseInt(scanner.nextLine().trim());

                        switch (option) {
                            case 1: {
                                System.out.println("New first name:");
                                String newValue = scanner.nextLine();
                                memberService.updateMemberInfo("first_name", newValue);
                                break;
                            }
                            case 2: {
                                System.out.println("New last name:");
                                String newValue = scanner.nextLine();
                                memberService.updateMemberInfo("last_name", newValue);
                                break;
                            }
                            case 3: {
                                System.out.println("New email:");
                                String newValue = scanner.nextLine();
                                memberService.updateMemberInfo("email", newValue);
                                break;
                            }
                            case 4: {
                                System.out.println("New password:");
                                String newValue = scanner.nextLine();
                                memberService.updateMemberInfo("password", newValue);
                                break;
                            }
                            case 0: {
                                current = false;
                                break;
                            }
                        }
                    }
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }

    public void adminMemberMenu() {
        boolean active = true;

        while (active) {
            System.out.println("---- Member Menu ----");
            System.out.println("1. See all members");
            System.out.println("2. Add new member");
            System.out.println("3. Suspend member");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: {
                   ArrayList<Member> members = memberService.getAllMembers();
                   for (Member member : members) {
                       System.out.println(member.toString());
                   }
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
