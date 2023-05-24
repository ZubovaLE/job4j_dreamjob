package ru.job4j.models;

public class Candidate extends User {
    public Candidate(CandidateBuilder builder) {
        super(builder);
    }

    public abstract static class CandidateBuilder extends AbstractUserBuilder {
        public CandidateBuilder(String lastName, String firstName) {
        }
    }

    @Override
    User build() {
        return null;
    }
}
