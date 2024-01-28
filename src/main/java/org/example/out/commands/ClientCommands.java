package org.example.out.commands;

/**
 * Enum со всеми командами для меню пользователя.
 * Используются в {@code MainView}
 * @see org.example.out.views.MainView
 */
public enum ClientCommands {
    /**
     * Описание команд написано вместе с ними же, если нужно посмотреть подробно, смотрите {@code README.md}
     */
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
