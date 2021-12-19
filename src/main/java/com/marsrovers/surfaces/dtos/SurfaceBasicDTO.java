package com.marsrovers.surfaces.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurfaceBasicDTO {

    @JsonProperty("id")
    private long id;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("extremeX")
    private Integer extremeX;

    @NotNull
    @JsonProperty("extremeY")
    private Integer extremeY;
}
