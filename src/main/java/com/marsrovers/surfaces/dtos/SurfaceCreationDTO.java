package com.marsrovers.surfaces.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marsrovers.rovers.dtos.RoverBasicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurfaceCreationDTO extends SurfaceBasicDTO {

    /*@JsonProperty("rovers")
    private Set<RoverBasicDTO> rovers;*/
}
