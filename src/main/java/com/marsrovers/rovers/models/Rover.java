package com.marsrovers.rovers.models;

import com.marsrovers.rovers.movement.Directions;
import com.marsrovers.surfaces.model.Surface;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Rover implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Column(unique = true)
    @Size(min = 3)
    private String name;

    @NotNull(message = "xPosition is mandatory")
    private Integer xPosition;

    @NotNull(message = "yPosition is mandatory")
    private Integer yPosition;

    @NotNull(message = "Direction is mandatory")
    private Directions direction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surface_id")
    // adding the surface object to know the boundaries where the rover can go up to
    private Surface surface;

}
