package ru.job4j.models;

import lombok.Getter;

@Getter
public class Candidate extends User {
    private String photo;
    private final Gender gender;

    private Candidate(CandidateBuilder builder) {
        super(builder);
        photo = builder.photo;
        gender = builder.gender;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static class CandidateBuilder extends AbstractUserBuilder {
        private String photo;
        private Gender gender;

        public CandidateBuilder(String lastName, String firstName) {
            super(lastName, firstName);
        }

        public CandidateBuilder withPhoto(String photo) {
            this.photo = photo;
            return this;
        }

        public CandidateBuilder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        @Override
        User build() {
            return new Candidate(this);
        }
    }
}
