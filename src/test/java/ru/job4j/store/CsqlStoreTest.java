package ru.job4j.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class CsqlStoreTest {

    @Test
    @DisplayName("When create candidate")
    public void whenCreateCandidate() {
        Store<Candidate> store = CsqlStore.instOf();
        Candidate candidate = new Candidate.CandidateBuilder(0, "Jackson", "James",
                LocalDateTime.of(2010, 8, 1, 1, 1))
                .withGender(Gender.MALE).build();
        store.save(candidate);
        Candidate candidateInDb = (Candidate) store.findById(candidate.getId());
        assertThat(candidateInDb.getLastName()).isEqualTo(candidate.getLastName());
    }

    @Test
    @DisplayName("When findAllCandidates")
    public void whenFindAllCandidates() {
        Store<Candidate> store = CsqlStore.instOf();
        Candidate candidateOne = new Candidate.CandidateBuilder(0, "Jackson", "James",
                LocalDateTime.of(2010, 8, 1, 1, 2))
                .withGender(Gender.MALE).build();
        Candidate candidateTwo = new Candidate.CandidateBuilder(0, "Clark", "Katy",
                LocalDateTime.of(2010, 8, 1, 1, 3))
                .withGender(Gender.FEMALE).build();
        store.save(candidateOne);
        store.save(candidateTwo);
        assertThat(store.findAll()).containsAll(List.of(candidateOne, candidateTwo));
    }
}