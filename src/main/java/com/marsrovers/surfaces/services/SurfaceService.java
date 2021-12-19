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

import java.util.*;
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
        if ((!surfaceRepository.findByName(surfaceCreationDTO.getName()).isEmpty()) || surfaceRepository.existsById(surfaceCreationDTO.getId())) {
            throw new AlreadyExistsEntityException("Surface already created.");
        }
        List<SurfaceGetCompleteDTO> surfaces = getSurfaces();
        for (SurfaceGetCompleteDTO surface : surfaces) {
            if (surface.getExtremeX().equals(surfaceCreationDTO.getExtremeX()) &&
                    surface.getExtremeY().equals(surfaceCreationDTO.getExtremeY())) {
                throw new AlreadyExistsEntityException("There is already a surface with these boundaries.");
            }
        }
        return surfaceMapper.surfaceToSurfaceBasicDTO(surfaceRepository.saveAndFlush(surfaceMapper.surfaceCreationDTOToSurface(surfaceCreationDTO)));
    }

    public String deleteSurface(long id) {
        //can not delete surface if rovers are present
        SurfaceGetRoversDTO surfaceDTO = getRovers(id);
        if (!surfaceDTO.getRovers().isEmpty()) {
            throw new EntityNotFoundException("Surface " + id + " has rovers on it, it can't be deleted. You need to delete the rovers first.");
        }
        surfaceRepository.deleteById(id);
        return "Surface was deleted.";
    }

    public SurfaceGetRoversDTO getRovers(long id) {
        return surfaceRepository.findById(id).map(surfaceMapper::surfaceToSurfaceGetRoversDTO).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Surface not found: " + id)
        );
    }

    public SurfaceBasicDTO getBoundaries(long id) {
        return surfaceRepository.findById(id).map(surfaceMapper::surfaceToSurfaceBasicDTO).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Surface not found: " + id)
        );
    }

    public Map<String, String> addRovers(SurfaceAddRoverDTO roversIdsDTO, long id, boolean surfaceAlreadyAddedToRover) {
        Surface surface = surfaceMapper.surfaceUpdateDTOToSurface(getSurface(id));
        Set<Rover> rovers = new HashSet<>();
        Map<String, String> roversStatus = new TreeMap<>();
        for (Long roverId : roversIdsDTO.getRoverIds()) {
            try {
                RoverGetCompleteDTO roverDTO = roverService.getRover(roverId);
                if (roverDTO != null) {
                    if (!surfaceAlreadyAddedToRover) { // if rover doesn't have the surface, first add it, then save the rovers set with the update objects
                        roverService.addSurface(surface.getId(), roverId, true);
                        roversStatus.put(roverId + " Success", "Rover " + roverId + " successfully added");
                    }
                    rovers.add(roverMapper.roverCompleteDTOToRover(roverDTO));
                }
            } catch (Exception e) {
                roversStatus.put(roverId + " Fail", e.getMessage());
            }
        }

        if (!rovers.isEmpty()) {
            surface.setRovers(rovers);
            surfaceRepository.save(surface);
        }
        return roversStatus;
    }

    public String deleteAllRoversFromSurface(long id) {
        return surfaceRepository.findById(id).map(surface -> {
            if (surface.getRovers().isEmpty()) {
                throw new EntityNotFoundException("This surface has no rovers.");
            }
            Set<Rover> rovers = surface.getRovers();
            rovers.clear();
            surfaceRepository.save(surface);
            return "Rovers from surface were deleted.";
        }).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Surface not found: " + id)
        );
    }

    public void deleteRoverFromSurface(Surface surface, long roverId) {
        Set<Rover> rovers = surface.getRovers();
        for (Rover rover : rovers) {
            if (rover.getId() == roverId) {
                rovers.remove(rover);
                surfaceRepository.save(surface);
                break;
            }
        }
    }
}
