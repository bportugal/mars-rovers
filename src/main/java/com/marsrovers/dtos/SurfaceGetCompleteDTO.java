package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class SurfaceGetCompleteDTO {

    @JsonProperty("surface")
    private SurfaceShortDTO surface;

    @JsonProperty("rovers")
    private Set<RoverBasicDTO> rovers;
}
