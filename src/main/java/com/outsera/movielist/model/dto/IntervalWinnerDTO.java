package com.outsera.movielist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntervalWinnerDTO {

    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;

}
