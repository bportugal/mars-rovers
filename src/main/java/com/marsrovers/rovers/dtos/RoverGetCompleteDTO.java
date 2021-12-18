package com.marsrovers.rovers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marsrovers.rovers.movement.Directions;
import com.marsrovers.surfaces.dtos.SurfaceBasicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoverGetCompleteDTO extends RepresentationModel<RoverGetCompleteDTO> {

    @JsonProperty("id")
    private long id;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("xPosition")
    private Integer xPosition;

    @NotNull
    @JsonProperty("yPosition")
    private Integer yPosition;

    @NotNull
    @JsonProperty("direction")
    private Directions direction;

    @JsonProperty("surface")
    private SurfaceBasicDTO surface;
}
