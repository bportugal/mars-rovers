package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RoverResponseDTO {

    @NotNull
    @JsonProperty("xPosition")
    private Integer xPosition;

    @NotNull
    @JsonProperty("yPosition")
    private Integer yPosition;

    @NotBlank
    @JsonProperty("direction")
    private String direction;
}
