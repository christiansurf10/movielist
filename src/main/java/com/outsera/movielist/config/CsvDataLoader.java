package com.outsera.movielist.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.outsera.movielist.model.Movie;
import com.outsera.movielist.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

@Configuration
public class CsvDataLoader {

    private static final Logger logger = LoggerFactory.getLogger(CsvDataLoader.class);

    @Bean
    CommandLineRunner loadCsvData(MovieRepository movieRepository) {
        return args -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(getClass().getResourceAsStream("/data.csv"))))) {

                // Ignora a primeira linha (cabeçalho) e processa as demais
                reader.lines().skip(1).forEach(line -> {
                    String[] fields = line.split(";");
                    boolean winner = false;
                    if(fields.length > 4)
                        winner = true;

                    Movie movie = new Movie(null, //id
                            Integer.parseInt(fields[0]), // release_year
                            fields[1], // title
                            fields[2], // studios
                            fields[3], // producer
                            winner
                    );

                    movieRepository.save(movie);
                });
                logger.info("CSV data import completed successfully.");
            } catch (Exception e) {
                logger.error("Error during CSV data import", e);
            }
        };
    }
}