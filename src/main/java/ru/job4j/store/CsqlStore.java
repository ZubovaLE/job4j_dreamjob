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

    private volatile boolean tableExists = false;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS candidates(id SERIAL PRIMARY KEY, " +
            "lastName TEXT, firstName TEXT, gender TEXT);";

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
        private static final Store<Candidate> INST = new CsqlStore();
    }

    public static Store<Candidate> instOf() {
        return CsqlStore.Lazy.INST;
    }

    @Override
    public Collection<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate.CandidateBuilder(it.getInt("id"),
                            it.getString("lastName"), it.getString("firstName"))
                            .withGender(Gender.valueOf(it.getString("gender"))).build());
                }
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
            checkCandidatesTable(cn);
            PreparedStatement ps = cn.prepareStatement("INSERT INTO candidates(lastName, firstName, gender) " +
                            "VALUES (?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, candidate.getLastName());
            ps.setString(2, candidate.getFirstName());
            ps.setString(3, candidate.getGender().toString());
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
            checkCandidatesTable(cn);
            PreparedStatement ps = cn.prepareStatement("UPDATE candidates SET lastName = ?, firstName = ? " +
                    "WHERE id = ?");
            ps.setString(1, candidate.getLastName());
            ps.setString(2, candidate.getFirstName());
            ps.setInt(3, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection()) {
            checkCandidatesTable(cn);
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidates WHERE id = ?");
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate.CandidateBuilder(it.getInt("id"), it.getString("lastName"),
                            it.getString("firstName"))
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
    public Candidate findByEmail(String email) {
        return null;
    }

    private synchronized void checkCandidatesTable(Connection cn) {
        if (!tableExists) {
            try {
                DatabaseMetaData metaData = cn.getMetaData();
                ResultSet resultSet = metaData.getTables(null, null, "candidates",
                        new String[]{"TABLES"});
                tableExists = resultSet.next();
                if (!tableExists) {
                    createCandidatesTable(cn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createCandidatesTable(Connection cn) {
        try (Statement statement = cn.createStatement()) {
            statement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
