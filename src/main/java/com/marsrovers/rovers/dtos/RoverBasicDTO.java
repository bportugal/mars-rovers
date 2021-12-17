package com.marsrovers.rovers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
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

    @NotBlank(message = "Direction is mandatory")
    @JsonProperty("direction")
    private String direction;
}
