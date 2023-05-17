package ru.job4j.models;

public class Candidate extends User {
    public Candidate(String lastName, String firstName, String middleName, String email, String number, int age) {
        super(lastName, firstName, middleName, email, number, age);
    }
}
