package com.outsera.movielist.service;


import com.outsera.movielist.model.Producer;
import com.outsera.movielist.model.Winner;
import com.outsera.movielist.model.dto.IntervalWinnerDTO;
import com.outsera.movielist.model.Movie;
import com.outsera.movielist.model.dto.ProducerWinnerDTO;
import com.outsera.movielist.repository.MovieRepository;
import com.outsera.movielist.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.data.domain.PageRequest;
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private final ProducerRepository producerRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, ProducerRepository producerRepository) {
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository;
    }

    public List<Movie> fetchAllMovies() {
        return movieRepository.findAll();
    }

    public ProducerWinnerDTO getProducerWinners() {
        ProducerWinnerDTO producerWinnerDTO = new ProducerWinnerDTO();

        PageRequest pageRequest = PageRequest.of(0, 1);
        List<Producer> producersMaxWinner = producerRepository.findTopByMaxAgeOutWinner(pageRequest);
        List<Producer> producersMinWinner = producerRepository.findTopByMinAgeOutWinner(pageRequest);
        List<IntervalWinnerDTO> maxWinners = new ArrayList<>();
        List<IntervalWinnerDTO> minWinners = new ArrayList<>();

        producersMaxWinner.forEach( producer -> {
            maxWinners.add(setIntervalWinnerDTO(producer));
        });
        producerWinnerDTO.setMax(maxWinners);

        producersMinWinner.forEach( producer -> {
            minWinners.add(setIntervalWinnerDTO(producer));
        });
        producerWinnerDTO.setMin(minWinners);

        return producerWinnerDTO;
    }

    private IntervalWinnerDTO setIntervalWinnerDTO(Producer producer) {
        IntervalWinnerDTO intervalWinnerDTO = new IntervalWinnerDTO();
        producer.getWinnerList().sort(Comparator.comparingInt(Winner::getYearWinner).reversed());
        Winner winner = producer.getWinnerList().getFirst();
        intervalWinnerDTO.setProducer(producer.getName());
        intervalWinnerDTO.setInterval(winner.getAgeOutWinner());
        intervalWinnerDTO.setPreviousWin(winner.getYearWinner() - winner.getAgeOutWinner());
        intervalWinnerDTO.setFollowingWin(winner.getYearWinner());
        return intervalWinnerDTO;
    }

}