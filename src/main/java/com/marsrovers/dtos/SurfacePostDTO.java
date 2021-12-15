package com.marsrovers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class SurfacePostDTO {

    @JsonProperty("id")
    private long id;

    @NotBlank
    @JsonProperty("extremeX")
    private BigDecimal extremeX;

    @NotBlank
    @JsonProperty("extremeY")
    private BigDecimal extremeY;
}
