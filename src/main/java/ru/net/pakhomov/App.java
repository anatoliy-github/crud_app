package ru.net.pakhomov;

import ru.net.pakhomov.ui.ConsoleUserInterface;
import ru.net.pakhomov.ui.UserInterface;

public class App {
    public static void main(String[] args) {
        UserInterface ui = new ConsoleUserInterface();
        ui.start();
    }
}
