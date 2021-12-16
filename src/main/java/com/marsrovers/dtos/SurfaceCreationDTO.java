package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class SurfaceCreationDTO extends SurfaceBasicDTO {

    @JsonProperty("rovers")
    private Set<RoverBasicDTO> rovers;
}
