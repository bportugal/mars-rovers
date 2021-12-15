package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SurfaceShortDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("extremeX")
    private Integer extremeX;

    @JsonProperty("extremeY")
    private Integer extremeY;
}
