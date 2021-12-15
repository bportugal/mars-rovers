package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoverBasicDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("xPosition")
    private BigDecimal xPosition;

    @JsonProperty("yPosition")
    private BigDecimal yPosition;

    @JsonProperty("direction")
    private String direction;
}
