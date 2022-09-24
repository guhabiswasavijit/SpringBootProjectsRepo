package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        URL url = Application.class.getClassLoader().getResource("schema.ddl");

        try (Scanner sc = new Scanner(url.openStream())) {
            sc.useDelimiter("\n");
            while (sc.hasNext()) {
                String line = sc.next().trim();
                jdbcTemplate.execute(line);
            }
        } catch (IOException e) {
            LOG.error("An error occurred when reading schema DDL from " + url, e);
        }

        List<Object[]> descriptions = Arrays.asList(
                new Object[]{"Javadoc"},
                new Object[]{"Application"}
        );

        jdbcTemplate.batchUpdate("INSERT INTO todos(description, done) VALUES (?, 0)", descriptions);

        jdbcTemplate.query("SELECT * FROM todos", (RowMapper<Object>) (rs, i) -> new Todo(rs.getLong("id"), rs.getString("description")))
                .forEach(System.out::println);
    }
}