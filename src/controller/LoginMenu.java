package controller;

import model.Artist;
import model.User;

import java.text.ParseException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    public static void run(Scanner scanner) throws ParseException {
        String line = scanner.nextLine();
        Matcher matcher = LoginMenu.getCommandMatcher(line, "back|exit");
        while (!matcher.matches()) {
            matcher = LoginMenu.getCommandMatcher(line, "register as user -u (?<username>[a-zA-Z0-9]+) -p (?<password>[a-zA-Z0-9]+)");
            if (matcher.matches()) {
                LoginMenu.userRegister(matcher);
                line = scanner.nextLine();
                matcher = LoginMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = LoginMenu.getCommandMatcher(line, "register as artist -u (?<username>[a-zA-Z0-9]+) -p (?<password>[a-zA-Z0-9]+) -n (?<nickname>[a-zA-Z0-9 ]+)");
            if (matcher.matches()) {
                LoginMenu.artistRegister(matcher);
                line = scanner.nextLine();
                matcher = LoginMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = LoginMenu.getCommandMatcher(line, "login as user -u (?<username>[a-zA-Z0-9]+) -p (?<password>[a-zA-Z0-9]+)");
            if (matcher.matches()) {
                LoginMenu.userLogin(matcher, scanner);
                line = scanner.nextLine();
                matcher = LoginMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = LoginMenu.getCommandMatcher(line, "login as artist -u (?<username>[a-zA-Z0-9]+) -p (?<password>[a-zA-Z0-9]+)");
            if (matcher.matches()) {
                LoginMenu.artistLogin(matcher, scanner);
                line = scanner.nextLine();
                matcher = LoginMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            matcher = LoginMenu.getCommandMatcher(line, "show menu name");
            if (matcher.matches()) {
                System.out.println("login menu");
                line = scanner.nextLine();
                matcher = LoginMenu.getCommandMatcher(line, "back|exit");
                continue;
            }
            System.out.println("invalid command");
            line = scanner.nextLine();
            matcher = LoginMenu.getCommandMatcher(line, "back|exit");
        }
        System.exit(0);
    }

    private static Matcher getCommandMatcher(String input, String regex) {
        return Pattern.compile(regex).matcher(input);
    }

    private static void userRegister(Matcher matcher) {
        if (User.getUserByUsername(matcher.group("username")) == null) {
            if (!isPasswordWeak(matcher)) {
                User user = new User(matcher.group("username"), matcher.group("password"));
                User.addUser(user);
                System.out.println("user registered successfully");
            } else
                System.out.println("password is not strong enough");
        } else
            System.out.println("username already exists");
    }

    private static void userLogin(Matcher matcher, Scanner scanner) {
        User user = User.getUserByUsername(matcher.group("username"));
        if (user != null) {
            if (Objects.equals(matcher.group("password"), user.getPassword())) {
                User.setLoggedInUser(User.getUserByUsername(matcher.group("username")));
                System.out.println("user logged in successfully");
                UserMenu.run(scanner);
            } else
                System.out.println("password is wrong");
        } else
            System.out.println("username doesn't exist");
    }

    private static void artistRegister(Matcher matcher) {
        if (Artist.getArtistByUsername(matcher.group("username")) == null) {
            if (!isPasswordWeak(matcher)) {
                Artist artist = new Artist(matcher.group("username"), matcher.group("password"), matcher.group("nickname"));
                Artist.addArtist(artist);
                System.out.println("artist registered successfully");
            } else
                System.out.println("password is not strong enough");
        } else
            System.out.println("username already exists");
    }

    private static void artistLogin(Matcher matcher, Scanner scanner) throws ParseException {
        Artist artist = Artist.getArtistByUsername(matcher.group("username"));
        if (artist != null) {
            if (Objects.equals(matcher.group("password"), artist.getPassword())) {
                Artist.setLoggedInArtist(Artist.getArtistByUsername(matcher.group("username")));
                System.out.println("artist logged in successfully");
                ArtistMenu.run(scanner);
            } else
                System.out.println("password is wrong");
        } else
            System.out.println("username doesn't exist");

    }

    private static boolean isPasswordWeak(Matcher matcher) {
        String password = matcher.group("password");
        Pattern englishLowercase = Pattern.compile("[a-z]");
        Pattern englishUppercase = Pattern.compile("[A-Z]");
        Pattern digit = Pattern.compile("[0-9]");
        return !englishLowercase.matcher(password).find() ||
                !englishUppercase.matcher(password).find() ||
                !digit.matcher(password).find();
    }
}
