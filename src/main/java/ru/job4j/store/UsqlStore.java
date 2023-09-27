package ru.job4j.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.models.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

public class UsqlStore implements Store<User> {
    private final BasicDataSource pool = new BasicDataSource();

    private UsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(UsqlStore.class.getClassLoader()
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
        private static final Store<User> INST = new UsqlStore();
    }

    public static Store<User> instOf() {
        return UsqlStore.Lazy.INST;
    }

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public void save(User user) {
        if (findByEmail(user.getEmail()) == null) {
            create(user);
        } else {
            update(user);
        }
    }

    private User create(User user) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("INSERT INTO users(name, email, password) VALUES(?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private void update(User user) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("UPDATE users SET name = ?, password = ? WHERE email = ?");
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(int id) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(it.getInt("id"), it.getString("name"),
                            it.getString("email"), it.getString("password"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement st = cn.prepareStatement("DELETE FROM users WHERE id = ?;")) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public User findByEmail(String email) {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("name"),
                            rs.getString("email"), rs.getString("password"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}