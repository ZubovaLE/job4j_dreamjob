package ru.job4j.models;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Candidate extends AbstractUser {
    private LocalDateTime created;
    private String photo;
    private final Gender gender;
    private int city_id;

    public Candidate(CandidateBuilder builder) {
        super(builder);
        created = builder.created;
        photo = builder.photo;
        gender = builder.gender;
        city_id = builder.city_id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static class CandidateBuilder extends AbstractUserBuilder {
        private LocalDateTime created;
        private String photo;
        private Gender gender;
        private int city_id;

        public CandidateBuilder(int id, String lastName, String firstName, LocalDateTime created) {
            super(id, lastName, firstName);
            this.created = created;
        }

        public CandidateBuilder withPhoto(String photo) {
            this.photo = photo;
            return this;
        }

        public CandidateBuilder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public CandidateBuilder withCity_id(int city_id) {
            this.city_id = city_id;
            return this;
        }

        @Override
        public Candidate build() {
            return new Candidate(this);
        }
    }
}
