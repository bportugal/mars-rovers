package com.marsrovers.services;

import com.marsrovers.dtos.SurfaceBasicDTO;
import com.marsrovers.dtos.SurfaceCreationDTO;
import com.marsrovers.dtos.SurfaceGetCompleteDTO;
import com.marsrovers.mappers.SurfaceMapper;
import com.marsrovers.models.Rover;
import com.marsrovers.models.Surface;
import com.marsrovers.repository.SurfaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SurfaceService {

    @Autowired
    private SurfaceRepository surfaceRepository;

    @Autowired
    private SurfaceMapper surfaceMapper;

    public SurfaceGetCompleteDTO getSurface(long id) {
        return null;
    }

    public List<SurfaceGetCompleteDTO> getSurfaces() {
        return null;
    }

    public Surface createSurface(SurfaceCreationDTO surfaceCreationDTO) {
        return null;
    }

    public SurfaceBasicDTO deleteSurface(long id) {
        return null;
    }

    public void deleteRoverFromSurface(Surface surface, Rover rover) {
        Set<Rover> rovers = surface.getRovers();
        rovers.remove(rover);
        surfaceRepository.save(surface);
    }
}
