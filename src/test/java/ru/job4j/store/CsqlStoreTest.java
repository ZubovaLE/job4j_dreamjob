package ru.job4j.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;
import ru.job4j.servlet.CandidateServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CsqlStoreTest {

    @Test
    @DisplayName("When create candidate")
    public void whenCreateCandidate() {
        Store<Candidate> store = CsqlStore.instOf();
        Candidate candidate = new Candidate.CandidateBuilder(0, "Jackson", "James")
                .withGender(Gender.MALE).build();
        store.save(candidate);
        Candidate candidateInDb = (Candidate) store.findById(candidate.getId());
        assertThat(candidateInDb.getLastName()).isEqualTo(candidate.getLastName());
    }

    @Test
    @DisplayName("When findAllCandidates")
    public void whenFindAllCandidates() {
        Store<Candidate> store = CsqlStore.instOf();
        Candidate candidateOne = new Candidate.CandidateBuilder(0, "Jackson", "James")
                .withGender(Gender.MALE).build();
        Candidate candidateTwo = new Candidate.CandidateBuilder(0, "Clark", "Katy")
                .withGender(Gender.FEMALE).build();
        store.save(candidateOne);
        store.save(candidateTwo);
        assertThat(store.findAll()).containsAll(List.of(candidateOne, candidateTwo));
    }
}