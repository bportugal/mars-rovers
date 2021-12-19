package com.marsrovers.surfaces.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurfaceAddRoverDTO {

    @NotBlank
    @JsonProperty("roverIds")
    private List<Long> roverIds;
}
