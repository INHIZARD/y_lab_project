package org.example.out.commands;

/**
 * Enum со всеми командами для меню логирования.
 * Используются в {@code MainView}
 * @see org.example.out.views.MainView
 */
public enum LoginCommands {
    /**
     * Описание команд написано вместе с ними же, если нужно посмотреть подробно, смотрите {@code README.md}
     */
    LOGIN("""
            >login [username] [password]
            Вход в систему
            """),
    SIGNUP("""
            >signup [username] [password]
            Регистрация
            """),
    EXIT("""
            >exit
            Выход из системы
            """);
    private final String command;

    LoginCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
