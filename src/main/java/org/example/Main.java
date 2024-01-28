package org.example;

import org.example.in.Session;

import java.util.Scanner;

/**
 * {@code Main} класс для запуска приложения
 */
public class Main {
    /**
     * Создание {@code Session} и помещение туда {@code Scanner}
     * @see Session#createSession()
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Session session = new Session(scanner);
            session.createSession();
        }
    }
}
