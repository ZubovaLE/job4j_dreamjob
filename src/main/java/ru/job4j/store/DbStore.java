package ru.job4j.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.models.Candidate;
import ru.job4j.models.Gender;
import ru.job4j.models.Post;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class DbStore implements Store {

    private static final DbStore instance = new DbStore();

    private final BasicDataSource pool = new BasicDataSource();

    private volatile boolean tableExists = false;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS candidates(id SERIAL PRIMARY KEY, " +
            "lastName TEXT, firstName TEXT, gender TEXT);";

    private DbStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(DbStore.class.getClassLoader()
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
        private static final Store INST = new DbStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Collection<Candidate> findAllCandidates() {
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

    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
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

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("UPDATE post SET name = ? WHERE id = ?")) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public Post findPostById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Candidate findCandidateById(int id) {
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
    public boolean delete(int id) {
        try (Connection conn = this.pool.getConnection();
             PreparedStatement st = conn.prepareStatement("DELETE FROM post WHERE id = ?;")) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
}