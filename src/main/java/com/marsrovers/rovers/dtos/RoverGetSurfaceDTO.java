package com.marsrovers.rovers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marsrovers.surfaces.dtos.SurfaceBasicDTO;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoverGetSurfaceDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("surface")
    private SurfaceBasicDTO surface;
}
