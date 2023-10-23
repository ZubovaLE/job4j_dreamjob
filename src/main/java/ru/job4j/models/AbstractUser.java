package ru.job4j.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class AbstractUser {
    private int id;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private String email;
    private final String phoneNumber;
    private int age;

    public AbstractUser(final AbstractUserBuilder userBuilder) {
        id = userBuilder.id;
        lastName = userBuilder.lastName;
        firstName = userBuilder.firstName;
        middleName = userBuilder.middleName;
        email = userBuilder.email;
        phoneNumber = userBuilder.phoneNumber;
        age = userBuilder.age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractUser abstractUser = (AbstractUser) o;
        return id == abstractUser.id;
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

        abstract AbstractUser build();
    }
}
