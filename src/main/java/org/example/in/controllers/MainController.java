package org.example.in.controllers;

import org.example.in.models.Counters;
import org.example.in.models.User;

import java.util.Scanner;

import static org.example.out.views.MainView.*;

public class MainController {
    private final Scanner scanner;

    public MainController(Scanner scanner) {
        this.scanner = scanner;
    }

    public String error() {
        errorView();
        return scanner.nextLine();
    }

    public String usernameError() {
        usernameErrorView();
        return scanner.nextLine();
    }

    public String loginError() {
        loginErrorView();
        return scanner.nextLine();
    }

    public String login() {
        loginView();
        return scanner.nextLine();
    }

    public String clientHome() {
        clientHomeView();
        return scanner.nextLine();
    }

    public String indications(User user) {
        indicationsView(user);
        return scanner.nextLine();
    }

    public String submit(boolean isSaved) {
        submitView(isSaved);
        return scanner.nextLine();
    }

    public String history(User user) {
        historyView(user);
        return scanner.nextLine();
    }

    public String historyOfDate(Counters indication) {
        historyOfDateView(indication);
        return scanner.nextLine();
    }

    public String inFuture(String date) {
        future(date);
        return scanner.nextLine();
    }
}
