package org.example.out.commands;

public enum LoginCommands {
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
