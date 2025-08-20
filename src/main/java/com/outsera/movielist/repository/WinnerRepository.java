package com.outsera.movielist.repository;

import com.outsera.movielist.model.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnerRepository extends JpaRepository<Winner, Long> {
}
