package org.example.out.views;

import org.example.in.models.Counters;
import org.example.in.models.User;
import org.example.out.commands.ClientCommands;
import org.example.out.commands.LoginCommands;

import java.util.Calendar;
import java.util.Map;

public class MainView {
    private static final String LINE = "---------------------------------------";

    private static void indicationsOutput(Counters indications) {
        Map<String, Integer> indication = indications.getCounters();
        for (String title : indication.keySet()) {
            System.out.println(title + ": " + indication.get(title));
        }
    }

    public static void errorView() {
        System.out.println(LINE);
        System.out.println("Неверная команда!\n");
        System.out.print(">");
    }

    public static void usernameErrorView() {
        System.out.println(LINE);
        System.out.println("Такое имя уже занято!\n");
        System.out.print(">");
    }

    public static void loginErrorView() {
        System.out.println(LINE);
        System.out.println("Неверные данные для входа!\n");
        System.out.print(">");
    }

    public static void loginView() {
        System.out.println(LINE);
        for (LoginCommands loginCommand : LoginCommands.values()) {
            System.out.println(loginCommand.getCommand());
        }
        System.out.print(">");
    }

    public static void clientHomeView() {
        System.out.println(LINE);
        for (ClientCommands clientCommand : ClientCommands.values()) {
            System.out.println(clientCommand.getCommand());
        }
        System.out.print(">");
    }

    public static void indicationsView(User user) {
        System.out.println(LINE);
        indicationsOutput(user.getIndications());
        System.out.print("\n>");
    }

    public static void submitView(boolean isSaved) {
        System.out.println(LINE);
        if (isSaved) {
            System.out.println("Ваши показатели были сохранены\n");
        } else {
            System.out.println("Вы уже сохраняли показатели в этом месяце\n");
        }
        System.out.print(">");
    }

    public static void historyView(User user) {
        System.out.println(LINE);
        Map<Calendar, Counters> indications = user.getSavedIndications();
        if (indications.isEmpty()) {
            System.out.println("Пока тут пусто\n");
        } else {
            for (Calendar date : indications.keySet()) {
                System.out.println(
                        date.get(Calendar.DAY_OF_MONTH) + "."
                                + (date.get(Calendar.MONTH) + 1) + "."
                                + date.get(Calendar.YEAR));
                System.out.println("----------");
                indicationsOutput(indications.get(date));
                System.out.println();
            }
        }
        System.out.print(">");
    }

    public static void historyOfDateView(Counters indication) {
        System.out.println(LINE);
        if (indication == null) {
            System.out.println("На такую дату данных нет");
        } else {
            indicationsOutput(indication);
        }
        System.out.print("\n>");
    }

    public static void future(String date) {
        System.out.println(LINE);
        System.out.println("Новая текущая дата: " + date + "\n");
        System.out.print(">");
    }
}
