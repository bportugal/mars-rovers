package com.marsrovers.repository;

import com.marsrovers.models.Rover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoverRepository extends JpaRepository<Rover, Long> {

}
