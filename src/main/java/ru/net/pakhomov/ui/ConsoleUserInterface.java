package ru.net.pakhomov.ui;

import ru.net.pakhomov.crud.Operations;
import ru.net.pakhomov.objects.Person;

import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface {
    private final String WELCOME = """
            ********************
            ** CRUD_APP START **
            ********************
            """;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final Scanner scanner;
    private final Operations operations;
    public ConsoleUserInterface() {
        scanner = new Scanner(System.in);
        operations = new Operations();
    }
    @Override
    public void start() {
        System.out.print(ANSI_BLUE + WELCOME + ANSI_RESET);
        showMenu();
        MenuItems item = getMenuItem();
        processOperation(item);
    }

    private void showMenu() {
        System.out.println(ANSI_GREEN + "Choose your option: " + ANSI_RESET);
        for(MenuItems item : MenuItems.values()) {
            System.out.println(item.getNumber() + " - " + item.getTitle());
        }
    }

    private MenuItems getMenuItem() {
        boolean tryAgain = true;
        do {
            try {
                int option = scanner.nextInt();
                return MenuItems.getByNumber(option);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while(tryAgain);
        return MenuItems.EXIT;
    }

    private void processOperation(MenuItems item) {
        switch(item) {
            case EXIT -> executeExit();
            case CREATE -> executeCreate();
            case READ -> executeRead();
            case UPDATE -> executeUpdate();
            case DELETE -> executeDelete();
        }
    }

    private void executeCreate() {
        System.out.println("Create new object: ");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        System.out.print("Name: ");
        String name = scanner.next();
        Person person = new Person(id, name);
        operations.save(person);
        //TODO: получать результат валидации на существование такого объекта
        // и при необходимости запускать update. иначе exit
    }

    private void executeRead() {
        System.out.print("Enter object ID: ");
        int id = scanner.nextInt();
        try {
            System.out.println(operations.getById(id));
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private void executeUpdate() {
        System.out.print("Enter object ID: ");
        int id = scanner.nextInt();
        try {
            System.out.println("Current object data: ");
            System.out.println(operations.getById(id));
            System.out.print("New ID: ");
            int newId = scanner.nextInt();
            System.out.print("New name: ");
            String newName = scanner.next();
            Person person = new Person(newId, newName);
            operations.update(person);
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void executeDelete() {
        System.out.print("Enter object ID: ");
        int id = scanner.nextInt();
        operations.delete(id);
    }

    private void executeExit() {
        System.out.println(ANSI_RED + "Exit" + ANSI_RESET);
    }

}
