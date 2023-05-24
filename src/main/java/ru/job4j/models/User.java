package ru.job4j.models;

import lombok.Getter;

@Getter
public abstract class User {
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final String email;
    private final String phoneNumber;

    private int age;

    private User(AbstractUserBuilder userBuilder) {
        lastName = userBuilder.lastName;
        firstName = userBuilder.firstName;
        middleName = userBuilder.middleName;
        email = userBuilder.email;
        phoneNumber = userBuilder.phoneNumber;
        age = userBuilder.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public abstract static class AbstractUserBuilder {
        private String lastName;
        private String firstName;
        private String middleName;
        private String email;
        private String phoneNumber;

        private int age;

        public AbstractUserBuilder withMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public AbstractUserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public AbstractUserBuilder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public AbstractUserBuilder withNumber(int age) {
            this.age = age;
            return this;
        }

        abstract User build(String lastName, String firstName);
    }
}
