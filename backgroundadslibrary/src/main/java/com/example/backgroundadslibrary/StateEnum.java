package com.example.backgroundadslibrary;

public enum StateEnum {
    TOP(1),
    BOTTOM(2),
    TOP_BOTTOM(3),
    FULL_SCREEN(4);

    private int mOption;

    StateEnum(int option) {
        mOption = option;
    }

    public int getInt() {
        return mOption;
    }
}