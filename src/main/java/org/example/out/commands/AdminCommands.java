package org.example.out.commands;

public enum AdminCommands {
    USERS("""
            >users
            Список всех пользователей
            """),
    HISTORY("""
            >history [username]
            Просмотр истории подачи показаний пользователя
            """),
    AUDIT("""
            >audit [username]
            Просмотр аудита пользователя
            """),
    ADD_INDICATION("""
            >indication [title]
            Добавление нового счетчика
            """),
    SET_ADMIN("""
            >sadmin [username]
            Изменить аккаунт пользователя на аккаунт администратора
            """),
    DELETE_ADMIN("""
            >dadmin [username]
            Изменить аккаунт администратора на аккаунт пользователя
            """),
    LOGOUT("""
            >logout
            Выйти из аккаунта
            """);
    private final String command;

    AdminCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
