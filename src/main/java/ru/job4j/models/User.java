package ru.job4j.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User {
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String number;

    private int age;
}
