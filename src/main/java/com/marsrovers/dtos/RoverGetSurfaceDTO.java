package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RoverGetSurfaceDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("surface")
    private SurfaceBasicDTO surface;
}
