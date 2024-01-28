package org.example;

import org.example.in.Session;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Session session = new Session(scanner);
            session.createSession();
        }
    }
}
