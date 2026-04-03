package library.repositories;

import library.models.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static library.controllers.MainController.loggedInUser;

public class MemberRepository {

    private final String URL = "jdbc:mysql://localhost:3306/bibliotek";
    private final String USER = "root";
    private final String PASS = "Apelsinkr0kant!";
    Scanner scanner = new Scanner(System.in);

    public Member getMemberByEmail(String email) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE email = ?")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return new Member(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getDate("membership_date").toLocalDate(), rs.getString("membership_type"), rs.getString("status"), rs.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }

    public void updateMemberInfo() {
        boolean active = true;
        while (active) {
            System.out.println("---- Update member info ----");
            System.out.println("1. Update name");
            System.out.println("2. Update email");
            System.out.println("3. Update password");
            System.out.println("0. Return");
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1: {
                    System.out.println("First name: ");
                    String firstName = scanner.nextLine();
                    System.out.println("Last name: ");
                    String lastName = scanner.nextLine();
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                         PreparedStatement stmt = conn.prepareStatement("UPDATE members SET first_name = ?, last_name = ? WHERE id = ?")) {
                        stmt.setString(1, firstName);
                        stmt.setString(2, lastName);
                        stmt.setInt(3, loggedInUser.getId());

                        stmt.executeUpdate();

                    } catch (SQLException e) {
                        System.out.println("Fel: " + e.getMessage());
                    }
                    break;
                }
                case 2: {
                    System.out.println("New email: ");
                    String newEmail = scanner.nextLine();
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                         PreparedStatement stmt = conn.prepareStatement("UPDATE members SET email = ? WHERE id = ?")) {
                        stmt.setString(1, newEmail);
                        stmt.setInt(2, loggedInUser.getId());
                        stmt.executeUpdate();

                    } catch (SQLException e) {
                        System.out.println("Fel: " + e.getMessage());
                    }
        break;
                }
                case 3: {
                    System.out.println("New password: ");
                    String newPassword = scanner.nextLine();
                    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                         PreparedStatement stmt = conn.prepareStatement("UPDATE members SET password = ? WHERE id = ?")) {
                        stmt.setString(1, newPassword);
                        stmt.setInt(2, loggedInUser.getId());
                        stmt.executeUpdate();

                    } catch (SQLException e) {
                        System.out.println("Fel: " + e.getMessage());
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

    public ArrayList<Member> getAllMembers() {
        ArrayList<Member> members = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM members");

            while (rs.next()) {
                members.add(new Member(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getDate("membership_date").toLocalDate(), rs.getString("membership_type"), rs.getString("status"), rs.getString("password")));
            }
            return members;

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }

    public Member getMemberById(int id) {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Member(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getDate("membership_date").toLocalDate(), rs.getString("membership_type"), rs.getString("status"), rs.getString("password"));
            }
        }
        catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }
}
