package com.marsrovers.rovers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marsrovers.surfaces.dtos.SurfaceCreationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoverCreationDTO extends RoverBasicDTO {

    /*@JsonProperty("surface")
    private SurfaceCreationDTO surface;*/

}
