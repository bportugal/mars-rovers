package com.marsrovers.surfaces.repository;

import com.marsrovers.surfaces.model.Surface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurfaceRepository extends JpaRepository<Surface, Long> {
    List<Surface> findByName(String name);
}
