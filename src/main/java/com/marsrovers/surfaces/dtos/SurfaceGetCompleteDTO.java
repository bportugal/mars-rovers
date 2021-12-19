package com.marsrovers.surfaces.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.marsrovers.rovers.dtos.RoverBasicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurfaceGetCompleteDTO extends RepresentationModel<SurfaceGetCompleteDTO> {

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

    @JsonProperty("rovers")
    private Set<RoverBasicDTO> rovers;
}
