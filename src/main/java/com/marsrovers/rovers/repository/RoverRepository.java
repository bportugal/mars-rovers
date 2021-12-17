package com.marsrovers.rovers.repository;

import com.marsrovers.rovers.models.Rover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoverRepository extends JpaRepository<Rover, Long> {
    Rover findByName(String name);
}
