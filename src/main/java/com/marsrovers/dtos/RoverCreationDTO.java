package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoverCreationDTO {

    @JsonProperty("rover")
    private RoverBasicDTO rover;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @JsonProperty("surface")
    private SurfacePostDTO surface;

}
