package com.marsrovers.rovers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marsrovers.rovers.movement.Directions;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoverBasicDTO {

    @JsonProperty("id")
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, message = "Name needs to have at least 3 chars")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "xPosition is mandatory")
    @JsonProperty("xPosition")
    private Integer xPosition;

    @NotNull(message = "yPosition is mandatory")
    @JsonProperty("yPosition")
    private Integer yPosition;

    @NotNull(message = "Direction is mandatory. Possible values: N, E, W, S")
    @JsonProperty("direction")
    private Directions direction;
}
