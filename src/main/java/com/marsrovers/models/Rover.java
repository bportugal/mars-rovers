package com.marsrovers.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Rover implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(unique = true)
    @Size(min = 3)
    private String name;

    private Integer xPosition;

    private Integer yPosition;

    private String direction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surface_id")
    // adding the surface object to know the boundaries where the rover can go up to
    private Surface surface;

}
