package com.marsrovers.rovers.repository;

import com.marsrovers.rovers.models.Rover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoverRepository extends JpaRepository<Rover, Long> {
    List<Rover> findByName(String name);
}
