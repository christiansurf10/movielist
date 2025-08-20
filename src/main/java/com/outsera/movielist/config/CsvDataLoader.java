package com.outsera.movielist.config;

import com.outsera.movielist.model.Producer;
import com.outsera.movielist.model.Winner;
import com.outsera.movielist.repository.ProducerRepository;
import com.outsera.movielist.repository.WinnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.outsera.movielist.model.Movie;
import com.outsera.movielist.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Configuration
public class CsvDataLoader {

    private static final Logger logger = LoggerFactory.getLogger(CsvDataLoader.class);
    @Value("${import.dir}")
    private String importDirPath;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private WinnerRepository winnerRepository;

    @Bean
    CommandLineRunner loadCsvData() {
        return args -> {
            boolean loaded = false;
            Path importDir = Paths.get(importDirPath).toAbsolutePath();
            if (Files.exists(importDir) && Files.isDirectory(importDir)) {
                try (Stream<Path> files = Files.list(importDir)) {
                    for (Path file : (Iterable<Path>) files.filter(f -> f.toString().endsWith(".csv"))::iterator) {
                        try (BufferedReader reader = Files.newBufferedReader(file)) {
                            processCsv(reader);
                            loaded = true;
                            logger.info("Importado arquivo externo: {}", file);
                        }
                    }
                } catch (IOException e) {
                    logger.error("Erro ao ler arquivos externos", e);
                }
            }
            if (!loaded) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream("/data.csv"))))) {
                    processCsv(reader);
                    logger.info("Importado arquivo do resource: /data.csv");
                } catch (Exception e) {
                    logger.error("Erro ao importar CSV do resource", e);
                }
            }
        };
    }
    private Producer getOrCreateProducer(String name) {
        return producerRepository.findByName(name)
                .orElseGet(() -> {
                    Producer producer = new Producer();
                    producer.setName(name);
                    return producerRepository.save(producer);
                });
    }

    private List<Producer> picPrducerFromImportWinnersTrue(String producers, int yearWinner){
        List<Producer> producerList = new ArrayList<>();
        for (String producerName : producers.split("\\s*,\\s*|\\sand\\s")){
            Producer producer = getOrCreateProducer(producerName.trim());
            Winner winner = new Winner();
            winner.setYearWinner(yearWinner);
            producer.getWinnerList().add(winnerRepository.save(winner));
            producerList.add(producerRepository.save(producer));
        }
        return producerList;
    }

    private void processCsv(BufferedReader reader) throws IOException {
        reader.lines().skip(1).forEach(line -> {
            String[] fields = line.split(";");
            boolean winner = fields.length > 4;
            int yearWinner = Integer.parseInt(fields[0]);
            Movie movie = new Movie(null,
                    yearWinner,
                    fields[1],
                    fields[2],
                    winner? this.picPrducerFromImportWinnersTrue(fields[3], yearWinner) : null,
                    winner
            );
            movieRepository.save(movie);
        });
        this.calculateProducerDiffSequentIntervaWinner();
    }

    private void calculateProducerDiffSequentIntervaWinner () {
        List<Producer> producersisWinnerMoreOfOne = producerRepository.findProducersWithMoreThanOneWinner();
        producersisWinnerMoreOfOne.forEach( producer -> {
            producer.getWinnerList().sort(Comparator.comparingInt(Winner::getYearWinner));
            producer.getWinnerList().stream()
                    .reduce((firstWinner, secondWinner) -> {
                        int diff = secondWinner.getYearWinner() - firstWinner.getYearWinner();
                        secondWinner.setAgeOutWinner(diff);
                        return  winnerRepository.save(secondWinner);
                    });

        });

        logger.info("Processamento de intervalo de vencedores finalizado.");
    }

}