package ru.job4j.models;

import lombok.Getter;

@Getter
public class HRManager extends AbstractUser {
    private HRManager(HRManagerBuilder builder) {
        super(builder);
    }

    public static class HRManagerBuilder extends AbstractUserBuilder {
        public HRManagerBuilder(int id, String lastName, String firstName) {
            super(id, lastName, firstName);
        }

        @Override
        AbstractUser build() {
            return new HRManager(this);
        }
    }
}
