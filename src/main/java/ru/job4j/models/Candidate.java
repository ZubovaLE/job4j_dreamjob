package ru.job4j.models;

import lombok.Getter;

@Getter
public class Candidate extends User {
    private Candidate(CandidateBuilder builder) {
        super(builder);
    }

    public static class CandidateBuilder extends AbstractUserBuilder {
        public CandidateBuilder(String lastName, String firstName) {
            super(lastName, firstName);
        }

        @Override
        User build() {
            return new Candidate(this);
        }
    }
}
