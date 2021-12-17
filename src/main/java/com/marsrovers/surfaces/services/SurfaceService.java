package com.marsrovers.surfaces.services;

import com.marsrovers.rovers.dtos.RoverGetCompleteDTO;
import com.marsrovers.rovers.mappers.RoverMapper;
import com.marsrovers.rovers.services.RoverService;
import com.marsrovers.surfaces.assembler.SurfaceModelAssembler;
import com.marsrovers.exceptions.AlreadyExistsEntityException;
import com.marsrovers.exceptions.EntityNotFoundException;
import com.marsrovers.surfaces.dtos.*;
import com.marsrovers.surfaces.mapper.SurfaceMapper;
import com.marsrovers.rovers.models.Rover;
import com.marsrovers.surfaces.model.Surface;
import com.marsrovers.surfaces.repository.SurfaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SurfaceService {

    @Autowired
    private SurfaceRepository surfaceRepository;

    @Autowired
    private SurfaceMapper surfaceMapper;

    @Autowired
    private SurfaceModelAssembler surfaceModelAssembler;

    @Autowired
    private RoverService roverService;

    @Autowired
    private RoverMapper roverMapper;

    public SurfaceGetCompleteDTO getSurface(long id) {
        Surface surface = surfaceRepository.findById(id).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Surface not found: " + id)
        );
        return surfaceModelAssembler.toModel(surface);
    }

    public List<SurfaceGetCompleteDTO> getSurfaces() {
        List<Surface> surfaces = surfaceRepository.findAll();
        return surfaces.stream().map(surface -> surfaceModelAssembler.toModel(surface)).collect(Collectors.toList());
    }

    public SurfaceBasicDTO createSurface(SurfaceCreationDTO surfaceCreationDTO) {
        if ((surfaceRepository.findByName(surfaceCreationDTO.getName()) == null) || surfaceRepository.existsById(surfaceCreationDTO.getId())) {
            throw new AlreadyExistsEntityException("Surface already created");
        }
        return surfaceMapper.surfaceToSurfaceBasicDTO(surfaceRepository.saveAndFlush(surfaceMapper.surfaceCreationDTOToSurface(surfaceCreationDTO)));
    }

    public String deleteSurface(long id) {
        //can not delete surface if rovers are present
        SurfaceGetRoversDTO surfaceDTO = getRovers(id);
        if (!surfaceDTO.getRovers().isEmpty()) {
            throw new EntityNotFoundException("Surface " + id + " has rovers on it, it can't be deleted. You need to delete the rovers first");
        }
        surfaceRepository.deleteById(id);
        return "Surface was deleted";
    }

    public SurfaceGetRoversDTO getRovers(long id) {
        return surfaceRepository.findById(id).map(surfaceMapper::surfaceToSurfaceGetRoversDTO).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Surface not found: " + id)
        );
    }

    public boolean canDeleteRoverFromSurface(Rover rover) {
        Surface surface = rover.getSurface();
        if (surface == null) {
            return true;
        }
        Set<Rover> rovers = surface.getRovers();
        if (rovers.isEmpty()) {
            return false;
        }
        rovers.remove(rover);
        surfaceRepository.save(surface);
        return true;
    }

    public SurfaceBasicDTO getBoundaries(long id) {
        return surfaceRepository.findById(id).map(surfaceMapper::surfaceToSurfaceBasicDTO).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Surface not found: " + id)
        );
    }

    public SurfaceGetCompleteDTO addRovers(SurfaceAddRoverDTO roversIdsDTO, long id) {
        Surface surface = surfaceMapper.surfaceUpdateDTOToSurface(getSurface(id));
        Set<Rover> rovers = new HashSet<>();
        for (Integer roverId : roversIdsDTO.getRoverIds()) {
            RoverGetCompleteDTO roverDTO = roverService.getRover(roverId);
            if (roverDTO != null) {
                roverService.addSurface(roverMapper.roverCompleteToRoverSurface(roverDTO));
                rovers.add(roverMapper.roverCompleteDTOToRover(roverDTO));
            }
        }
        surface.setRovers(rovers);
        surfaceRepository.save(surface);
        return getSurface(id);
    }

    public String deleteAllRoversFromSurface(long id) {
        return surfaceRepository.findById(id).map(surface -> {
            for (Rover rover : surface.getRovers()) {
                roverService.deleteRoverFromSurface(rover.getId());
            }
            /*surface.setRovers(null);
            surfaceRepository.save(surface);*/
            return "Rovers from surface were deleted";
        }).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Surface not found: " + id)
        );
    }
}
