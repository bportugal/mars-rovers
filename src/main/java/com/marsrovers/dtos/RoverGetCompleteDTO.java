package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoverGetCompleteDTO extends RoverBasicDTO {

    @JsonProperty("surface")
    private SurfaceBasicDTO surface;
}