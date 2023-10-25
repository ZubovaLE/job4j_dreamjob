package ru.job4j.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.models.Candidate;
import ru.job4j.models.City;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class CityStore implements Store<City> {
    private final BasicDataSource pool = new BasicDataSource();

    public CityStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(CityStore.class.getClassLoader().getResourceAsStream("db.properties"))
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
        pool.setUrl("jdbc.url");
        pool.setUsername("jdbc.username");
        pool.setPassword("jdbc.password");
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store<City> INST = new CityStore();
    }

    public static Store<City> instOf() {
        return CityStore.Lazy.INST;
    }

    @Override
    public Collection<City> findAll() {
        List<City> cities = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM cities;")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cities.add(new City(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

    @Override
    public void save(City city) {
        int id = city.getId();
        if (id == 0) {
            create(city);
        } else {
            update(city);
        }
    }

    private City create(City city) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("INSERT INTO cities(name) VALUES(?);",
                    PreparedStatement.RETURN_GENERATED_KEYS)
            ps.setString(1, city.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    city.setId(id.getInt(1));
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    private void update(City city) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("UPDATE cities SET name = ? WHERE id = ?;")
            ps.setString(1, city.getName());
            ps.setInt(2, city.getId());
            ps.execute();
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public City findById(int id) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM cities WHERE id = ?;");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new City(rs.getInt("id"), rs.getString("name"));
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public City findByName(String name) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        try (Connection cn = pool.getConnection()) {
            PreparedStatement ps = cn.prepareStatement("DELETE FROM cities  WHERE id = ?;");
            ps.setInt(1, id);
            return (ps.executeUpdate() > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
