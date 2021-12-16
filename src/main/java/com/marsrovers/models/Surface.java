package com.marsrovers.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private Integer extremeX;

    @NotNull
    private Integer extremeY;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "surface")
    private Set<Rover> rovers = new HashSet<>();

}
