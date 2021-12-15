package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoverGetCompleteDTO {

    @JsonProperty("rover")
    private RoverBasicDTO rover;

    @JsonProperty("surface")
    private SurfaceShortDTO surface;
}
