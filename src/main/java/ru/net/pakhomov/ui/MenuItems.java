package ru.net.pakhomov.ui;

public enum MenuItems {
    CREATE(1, "create and save new object"),
    READ(2, "read object(s) from database"),
    UPDATE(3, "update an existing object"),
    DELETE(4, "delete object"),
    EXIT(0, "exit");

    private final int number;
    private final String title;

    MenuItems(int number, String title) {
        this.number = number;
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public static MenuItems getByNumber(int number) {
        for(MenuItems item : MenuItems.values()) {
            if(item.getNumber() == number) {
                return item;
            }
        }
        throw new IllegalArgumentException("Wrong menu item. Try again: ");
    }

}
