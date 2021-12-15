package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RoverGetSurfaceDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("surface")
    private SurfaceShortDTO surface;
}
