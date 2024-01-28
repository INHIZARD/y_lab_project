package org.example.in;

import org.example.in.controllers.MainController;
import org.example.in.models.User;
import org.example.in.services.MainService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Session {
    private final MainController mainController;
    private final MainService mainService;
    private final List<User> users;

    public Session(Scanner scanner) {
        this.mainController = new MainController(scanner);
        this.mainService = new MainService();
        User admin = new User("admin", "admin", mainService.getIndications());
        admin.setStatus(true);
        this.users = new ArrayList<>(List.of(admin));
    }

    public void createSession() {
        loginSession();
    }

    private void loginSession() {
        String command = mainController.login();
        User user = null;
        boolean exit = false;
        while (user == null) {
            if (Pattern.matches("login \\w* \\w*", command)) {
                if (mainService.loginUser(command, users)) {
                    user = mainService.getUserWithPassword(command, users);
                } else {
                    command = mainController.loginError();
                }
            } else if (Pattern.matches("signup \\w* \\w*", command)) {
                if (mainService.signupUser(command, users)) {
                    user = mainService.getUserWithPassword(command, users);
                } else {
                    command = mainController.usernameError();
                }
            } else if (Pattern.matches("help", command)) {
                command = mainController.login();
            } else if (Pattern.matches("exit", command)) {
                exit = true;
                break;
            } else {
                command = mainController.error();
            }
        }

        if (exit) {
            return;
        }

        if (user.isStatus()) {
            mainService.audit(user, command);
            adminHomeSession(user);
        } else {
            mainService.audit(user, command);
            clientHomeSession(user);
        }
    }

    private void clientHomeSession(User user) {
        String command = mainController.clientHome();
        boolean logout = false;
        while (!logout) {
            mainService.audit(user, command);
            if (Pattern.matches("indications", command)) {
                command = mainController.indications(user);
            } else if (Pattern.matches("submit", command)) {
                command = mainController.submit(mainService.save(user));
            } else if (Pattern.matches("history", command)) {
                command = mainController.history(user);
            } else if (Pattern.matches("history \\d\\d.\\d\\d\\d\\d", command)) {
                command = mainController.historyOfDate(mainService.hasDataAt(user, command).orElse(null));
            } else if (Pattern.matches("logout", command)) {
                logout = true;
            } else if (Pattern.matches("infuture \\d*", command)) {
                command = mainController.inFuture(mainService.addTime(command, users));
            } else if (Pattern.matches("help", command)) {
                command = mainController.clientHome();
            } else {
                command = mainController.error();
            }
        }
        loginSession();
    }

    private void adminHomeSession(User user) {
        String command = mainController.adminHome();
        boolean logout = false;
        while (!logout) {
            mainService.audit(user, command);
            if (Pattern.matches("users", command)) {
                command = mainController.allUsers(users);
            } else if (Pattern.matches("history \\w*", command)) {
                command = mainController.history(mainService.getUserWithoutPassword(command, users));
            } else if (Pattern.matches("audit \\w*", command)) {
                command = mainController.audit(mainService.getUserWithoutPassword(command, users));
            } else if (Pattern.matches("indication \\w*", command)) {
                mainService.setIndication(command, users);
                command = mainController.newIndication();
            } else if (Pattern.matches("sadmin \\w*", command)) {
                command = mainController.newAdmin(mainService.setAdmin(command, users));
            } else if (Pattern.matches("dadmin \\w*", command)) {
                command = mainController.backAdmin(mainService.deleteAdmin(command, users, user));
            } else if (Pattern.matches("logout", command)) {
                logout = true;
            } else if (Pattern.matches("infuture \\d*", command)) {
                command = mainController.inFuture(mainService.addTime(command, users));
            } else if (Pattern.matches("help", command)) {
                command = mainController.adminHome();
            } else {
                command = mainController.error();
            }
        }
        loginSession();
    }
}
