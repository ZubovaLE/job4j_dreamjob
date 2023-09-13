package ru.job4j.models;

import lombok.Getter;

@Getter
public class Candidate extends AbstractUser {
    private String photo;
    private final Gender gender;

    public Candidate(CandidateBuilder builder) {
        super(builder);
        photo = builder.photo;
        gender = builder.gender;
        photo = "";
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static class CandidateBuilder extends AbstractUserBuilder {
        private String photo;
        private Gender gender;

        public CandidateBuilder(int id, String lastName, String firstName) {
            super(id, lastName, firstName);
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
        public Candidate build() {
            return new Candidate(this);
        }
    }
}
