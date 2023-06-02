package ru.job4j.models;

import lombok.Getter;

@Getter
public class HRManager extends User {
    private HRManager(HRManagerBuilder builder) {
        super(builder);
    }

    public static class HRManagerBuilder extends AbstractUserBuilder {
        public HRManagerBuilder(String lastName, String firstName) {
            super(lastName, firstName);
        }

        @Override
        User build() {
            return new HRManager(this);
        }
    }

    public static void main(String[] args) {
        User manager = new HRManagerBuilder("l", "rr").withAge(21).build();
        System.out.println(manager.getFirstName());
    }
}
