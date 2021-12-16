package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoverCreationDTO extends RoverBasicDTO{

    @JsonProperty("surface")
    private SurfaceCreationDTO surface;

}
