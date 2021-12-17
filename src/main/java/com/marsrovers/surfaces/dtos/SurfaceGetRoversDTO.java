package com.marsrovers.surfaces.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marsrovers.rovers.dtos.RoverBasicDTO;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurfaceGetRoversDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("rovers")
    private Set<RoverBasicDTO> rovers;

}
