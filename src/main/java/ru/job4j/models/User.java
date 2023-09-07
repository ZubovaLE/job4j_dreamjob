package ru.job4j.models;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class User {
    private int id;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private String email;
    private final String phoneNumber;
    private String password;
    private int age;

    public User(final AbstractUserBuilder userBuilder) {
        id = userBuilder.id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract static class AbstractUserBuilder {
        private final int id;
        private final String lastName;
        private final String firstName;
        private String middleName;
        private String email;
        private String phoneNumber;
        private int age;

        public AbstractUserBuilder(int id, String lastName, String firstName) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
        }

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

        public AbstractUserBuilder withAge(int age) {
            this.age = age;
            return this;
        }

        abstract User build();
    }
}
