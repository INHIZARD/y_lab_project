package org.example.in;

import org.example.in.controllers.MainController;
import org.example.in.models.User;
import org.example.in.services.MainService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Класс {@code Session} предназначен для инициализации сессии приложения.
 * В сессии прописан сценарий приложения, а так же она взаимодействует с
 * {@code MainController} и {@code MainService}
 * @see MainController
 * @see MainService
 */
public class Session {
    private final MainController mainController;
    private final MainService mainService;
    /**
     * Поле для хранения всех созданных пользователей
     */
    private final List<User> users;

    /**
     * Единственный конструктор класса {@code Session}, в котором инициализируются
     * {@code MainController}, {@code MainService} и {@code users}.
     * Для удобства создается один администратор, так как без его создания функции администратора в приложении
     * были бы скрыты
     */
    public Session(Scanner scanner) {
        this.mainController = new MainController(scanner);
        this.mainService = new MainService();
        User admin = new User("admin", "admin", mainService.getIndications());
        admin.setStatus(true);
        this.users = new ArrayList<>(List.of(admin));
    }

    /**
     * Создание сессии.
     * В функции вызывается метод {@code loginSession()}, который демонстрирует вход в приложение
     * @see Session#loginSession()
     */
    public void createSession() {
        loginSession();
    }

    /**
     * Метод реализующий логику входа в приложение.
     * Есть несколько сценариев развития приложения, которые зависят от переменной {@code command}.
     * Все сценарии реализованы в {@code MainController}
     * @see MainController
     */
    private void loginSession() {
        // Демонстрация главного экрана входа
        String command = mainController.login();
        // Переменная для хранения пользователя, которого получим
        // при регистрации или входе
        User user = null;
        // Значение, которое отвечает за выход из приложения
        boolean exit = false;
        while (user == null) {
            // Анализ команд, которые соответственно отвечают за вход в систему, регистрацию,
            // вызов меню, выход. Все остальные команды выводят меню ошибки
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

        // Анализ статуса пользователя (администратор или нет) и вызов соответствующей сессии для него
        if (user.isStatus()) {
            mainService.audit(user, command);
            adminHomeSession(user);
        } else {
            mainService.audit(user, command);
            clientHomeSession(user);
        }
    }

    /**
     * Метод реализующий сессию пользователя без прав администратора.
     * Все сценарии реализованы в {@code MainController}
     * @see MainController
     */
    private void clientHomeSession(User user) {
        // Демонстрация меню для пользователя
        String command = mainController.clientHome();
        // Переменная отвечающая за выход из аккаунта
        boolean logout = false;
        while (!logout) {
            // Аудит действий пользователя
            mainService.audit(user, command);
            // Анализ команд пользователя
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
        // Выход из цикла означает выход из аккаунта, соответственно нужно вернуться в меню входа
        loginSession();
    }

    /**
     * Метод реализующий сессию пользователя с правами администратора.
     * Все сценарии реализованы в {@code MainController}
     * @see MainController
     */
    private void adminHomeSession(User user) {
        // Демонстрация меню для администратора
        String command = mainController.adminHome();
        // Переменная отвечающая за выход из аккаунта
        boolean logout = false;
        while (!logout) {
            // Аудит действий пользователя
            mainService.audit(user, command);
            // Анализ команд пользователя
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
        // Выход из цикла означает выход из аккаунта, соответственно нужно вернуться в меню входа
        loginSession();
    }
}
