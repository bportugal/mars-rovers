package com.marsrovers.rovers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoverCommandDTO {

    @NotNull
    @JsonProperty("id")
    private long id;

    @NotBlank
    @JsonProperty("commands")
    private String commands;
}
