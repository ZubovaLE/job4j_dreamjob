package ru.job4j.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CsqlStoreTest {

    @Test
    @DisplayName("When create candidate")
    public void whenCreateCandidate() {
        Store store = CsqlStore.instOf();
        Candidate candidate = new Candidate.CandidateBuilder(0, "Jackson", "James")
                .withGender(Gender.MALE).build();
        store.save(candidate);
        Candidate candidateInDb = (Candidate) store.findById(candidate.getId());
        assertThat(candidateInDb.getLastName()).isEqualTo(candidate.getLastName());
    }

    @Test
    @DisplayName("When findAllCandidates")
    public void whenFindAllCandidates() {
        Store store = PsqlStore.instOf();
        Candidate candidateOne = new Candidate.CandidateBuilder(0, "Jackson", "James")
                .withGender(Gender.MALE).build();
        Candidate candidateTwo = new Candidate.CandidateBuilder(0, "Clark", "Katy")
                .withGender(Gender.FEMALE).build();
        store.save(candidateOne);
        store.save(candidateTwo);
        assertThat(store.findAll()).containsAll(List.of(candidateOne, candidateTwo));
    }
}