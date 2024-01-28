package org.example.in.controllers;

import org.example.in.models.Counters;
import org.example.in.models.User;

import java.util.List;
import java.util.Scanner;

import static org.example.out.views.MainView.*;

/**
 * Класс контроллер, отвечающий за эндпоинты приложения.
 * Все визуальные реализации находятся в классе {@code MainView}.
 * Все методы контроллера вызываются из {@code Session}
 * @see org.example.out.views.MainView
 * @see org.example.in.Session
 */
public class MainController {
    private final Scanner scanner;

    public MainController(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Ошибка связанная с командой
     */
    public String error() {
        errorView();
        return scanner.nextLine();
    }

    /**
     * Ошибка связанная с {@code username}
     */
    public String usernameError() {
        usernameErrorView();
        return scanner.nextLine();
    }

    /**
     * Ошибка логирования
     */
    public String loginError() {
        loginErrorView();
        return scanner.nextLine();
    }

    /**
     * Логирование
     */
    public String login() {
        loginView();
        return scanner.nextLine();
    }

    /**
     * Меню пользователя
     */
    public String clientHome() {
        clientHomeView();
        return scanner.nextLine();
    }

    /**
     * Список показаний счетчиков
     */
    public String indications(User user) {
        indicationsView(user);
        return scanner.nextLine();
    }

    /**
     * Подача показаний
     */
    public String submit(boolean isSaved) {
        submitView(isSaved);
        return scanner.nextLine();
    }

    /**
     * История подачи показаний
     */
    public String history(User user) {
        historyView(user);
        return scanner.nextLine();
    }

    /**
     * История подачи показаний за дату
     */
    public String historyOfDate(Counters indication) {
        historyOfDateView(indication);
        return scanner.nextLine();
    }

    /**
     * Увеличение текущей даты
     */
    public String inFuture(String date) {
        future(date);
        return scanner.nextLine();
    }

    /**
     * Меню админа
     */
    public String adminHome() {
        adminHomeView();
        return scanner.nextLine();
    }

    /**
     * Список всех пользователей
     */
    public String allUsers(List<User> users) {
        allUsersView(users);
        return scanner.nextLine();
    }

    /**
     * Аудит пользователя
     */
    public String audit(User user) {
        auditView(user);
        return scanner.nextLine();
    }

    /**
     * Новый счетчик
     */
    public String newIndication() {
        newIndicationView();
        return scanner.nextLine();
    }

    /**
     * Добавление прав администратора
     */
    public String newAdmin(boolean isNewAdmin) {
        newAdminView(isNewAdmin);
        return scanner.nextLine();
    }

    /**
     * Удаление прав администратора
     */
    public String backAdmin(boolean isOldAdmin) {
        backAdminView(isOldAdmin);
        return scanner.nextLine();
    }
}
