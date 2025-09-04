package com.outsera.movielist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerWinnerDTO {

    private List<IntervalWinnerDTO> min = new ArrayList<IntervalWinnerDTO>(1  );
    private List<IntervalWinnerDTO> max = new ArrayList<IntervalWinnerDTO>(1  );

}
