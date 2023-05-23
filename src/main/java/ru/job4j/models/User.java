package ru.job4j.models;

import lombok.Getter;

@Getter
public class User {
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String phoneNumber;

    private int age;

    private User(UserBuilder userBuilder) {
        lastName = userBuilder.lastName;
        firstName = userBuilder.firstName;
        middleName = userBuilder.middleName;
        email = userBuilder.email;
        phoneNumber = userBuilder.phoneNumber;
    }

    private static class UserBuilder {
        private String lastName;
        private String firstName;
        private String middleName;
        private String email;
        private String phoneNumber;

        private int age;

        public UserBuilder(String lastName, String firstName) {
            this.lastName = lastName;
            this.firstName = firstName;
        }

        public UserBuilder withMiddleName(String middleName){
            this.middleName = middleName;
            return this;
        }

        public UserBuilder withEmail(String email){
            this.email = email;
            return this;
        }

        public UserBuilder withPhoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder withNumber(int age){
            this.age = age;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
