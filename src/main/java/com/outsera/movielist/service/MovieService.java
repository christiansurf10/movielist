package com.outsera.movielist.service;


import com.outsera.movielist.model.Movie;
import com.outsera.movielist.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> fetchAllMovies() {
        return movieRepository.findAll();
    }
}