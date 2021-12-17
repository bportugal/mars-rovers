package com.marsrovers.surfaces.model;

import com.marsrovers.rovers.models.Rover;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Surface implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Column(unique = true)
    @Size(min = 3)
    private String name;

    @NotNull
    private Integer extremeX;

    @NotNull
    private Integer extremeY;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surface")
    private Set<Rover> rovers = new HashSet<>();

    @Override
    public String toString() {
        return "Surface{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", extremeX=" + extremeX +
                ", extremeY=" + extremeY +
                ", rovers=" + rovers +
                '}';
    }
}
