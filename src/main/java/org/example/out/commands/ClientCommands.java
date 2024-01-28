package org.example.out.commands;

public enum ClientCommands {
    GET_INDICATIONS("""
            >indications
            Посмотреть показания всех счетчиков
            """),
    SUBMIT_INDICATIONS("""
            >submit
            Подать показания
            """),
    HISTORY_OF_INDICATIONS("""
            >history
            Посмотреть историю подачи показаний
            """),
    INDICATION("""
            >history [MM.yyyy]
            Посмотреть данные счетчиков в конкретный месяц
            """),
    LOGOUT("""
            >logout
            Выйти из аккаунта
            """);
    private final String command;

    ClientCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
