package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class SurfaceGetRoversDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("rovers")
    private Set<RoverBasicDTO> rovers;

}
