package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SurfaceBasicDTO {

    @JsonProperty("id")
    private long id;

    @NotNull
    @JsonProperty("extremeX")
    private Integer extremeX;

    @NotNull
    @JsonProperty("extremeY")
    private Integer extremeY;
}
