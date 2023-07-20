package ru.job4j.models;

public enum Gender {
    MALE, FEMALE;

    @Override
    public String toString() {
        switch (this.ordinal()) {
            case 0:
                return "MALE";
            case 1:
                return "FEMALE";
            default:
                return null;
        }
    }
}
