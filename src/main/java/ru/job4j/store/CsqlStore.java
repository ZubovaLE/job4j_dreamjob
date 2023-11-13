package ru.job4j.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class CsqlStore implements Store<Candidate> {

    private final BasicDataSource pool = new BasicDataSource();

    private CsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(PsqlStore.class.getClassLoader()
                                .getResourceAsStream("db.properties"))
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final CsqlStore INST = new CsqlStore();
    }

    public static CsqlStore instOf() {
        return CsqlStore.Lazy.INST;
    }

    @Override
    public Collection<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates")) {
            try (ResultSet it = ps.executeQuery()) {
                collectCandidates(candidates, it);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("INSERT INTO candidates(lastName, firstName, created, gender, photo) " +
                            "VALUES (?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, candidate.getLastName());
            ps.setString(2, candidate.getFirstName());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setString(4, candidate.getGender().toString());
            ps.setString(5, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("UPDATE candidates SET lastName = ?, firstName = ?, photo = ? " +
                    "WHERE id = ?");
            ps.setString(1, candidate.getLastName());
            ps.setString(2, candidate.getFirstName());
            ps.setString(3, candidate.getPhoto());
            ps.setInt(4, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates WHERE id = ?");
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate.CandidateBuilder(it.getInt("id"), it.getString("lastName"),
                            it.getString("firstName"), it.getTimestamp("created").toLocalDateTime())
                            .withPhoto(it.getString("photo"))
                            .withGender(Gender.valueOf(it.getString("gender")))
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("DELETE FROM candidates  WHERE id = ?;");
            ps.setInt(1, id);
            return (ps.executeUpdate() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Candidate> findTodayCandidates() {
        List<Candidate> todayCandidates = new ArrayList<>();
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates WHERE created >= date_trunc('DAY', current_date)");
            try (ResultSet rs = ps.executeQuery()) {
                collectCandidates(todayCandidates, rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todayCandidates;
    }

    private void collectCandidates(List<Candidate> todayCandidates, ResultSet rs) throws SQLException {
        while (rs.next()) {
            todayCandidates.add(new Candidate.CandidateBuilder(rs.getInt("id"),
                    rs.getString("lastName"), rs.getString("firstName"),
                    rs.getTimestamp("created").toLocalDateTime())
                    .withPhoto(rs.getString("photo"))
                    .withGender(Gender.valueOf(rs.getString("gender")))
                    .build());
        }
    }
}