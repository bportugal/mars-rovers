package com.marsrovers.rovers.services;

import com.marsrovers.rovers.movement.Commands;
import com.marsrovers.rovers.movement.RoverMovement;
import com.marsrovers.rovers.assembler.RoverModelAssembler;
import com.marsrovers.exceptions.AlreadyExistsEntityException;
import com.marsrovers.exceptions.EntityNotFoundException;
import com.marsrovers.rovers.dtos.*;
import com.marsrovers.rovers.mappers.RoverMapper;
import com.marsrovers.rovers.models.Rover;
import com.marsrovers.rovers.repository.RoverRepository;
import com.marsrovers.surfaces.dtos.SurfaceAddRoverDTO;
import com.marsrovers.surfaces.dtos.SurfaceGetCompleteDTO;
import com.marsrovers.surfaces.mapper.SurfaceMapper;
import com.marsrovers.surfaces.model.Surface;
import com.marsrovers.surfaces.services.SurfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoverService {

    @Autowired
    private RoverRepository roverRepository;

    @Autowired
    private RoverMapper roverMapper;

    @Autowired
    private RoverModelAssembler roverModelAssembler;

    @Autowired
    private RoverMovement roverMovement;

    @Autowired
    private SurfaceService surfaceService;

    @Autowired
    private SurfaceMapper surfaceMapper;

    public RoverGetCompleteDTO getRover(long id) {
        Rover rover = roverRepository.findById(id).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Rover not found: " + id)
        );
        return roverModelAssembler.toModel(rover);
    }

    public List<RoverGetCompleteDTO> getRovers() {
        List<Rover> rovers = roverRepository.findAll();
        return rovers.stream().map(rover -> roverModelAssembler.toModel(rover)).collect(Collectors.toList());

        // return roverMapper.mapListRoverToListRoverCompleteDTO(roverRepository.findAll());
        // return roverRepository.findAll().stream().map(roverMapper::roverToRoverCompleteDTO).collect(Collectors.toList());
    }

    public RoverBasicDTO createRover(RoverCreationDTO roverCreationDTO) {
        if ((roverRepository.findByName(roverCreationDTO.getName()).size() > 0) || roverRepository.existsById(roverCreationDTO.getId())) {
            throw new AlreadyExistsEntityException("Rover already created.");
        }
        List<Rover> rovers = roverRepository.findAll();
        for (Rover rov : rovers) {
            if (rov.getXPosition().equals(roverCreationDTO.getXPosition()) && rov.getYPosition().equals(roverCreationDTO.getYPosition())) {
                throw new IllegalArgumentException("There is already a rover in this position");
            }
        }
        return roverMapper.roverToRoverBasicDTO(roverRepository.saveAndFlush(roverMapper.roverCreationDTOToRover(roverCreationDTO)));
    }

    public String deleteRover(long id) {
        return deleteSurfaceOfRover(id);
    }

    private String deleteSurfaceOfRover(long id) {
        return roverRepository.findById(id).map(rover -> {
            Surface surface = rover.getSurface();
            if (surface != null) {
                surfaceService.deleteRoverFromSurface(surface, id);
            }
            roverRepository.deleteById(id);
            return "Rover was deleted.";
        }).orElseThrow(() -> new EntityNotFoundException("Rover not found to be deleted: " + id));
    }

    public RoverGetSurfaceDTO getSurfaceFromRover(long id) {
        return roverRepository.findById(id).map(roverMapper::roverToRoverSurfaceDTO).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Rover not found: " + id)
        );
    }

    public RoverBasicDTO getCurrentPosition(long id) {
        return roverRepository.findById(id).map(roverMapper::roverToRoverBasicDTO).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Rover not found: " + id)
        );
    }

    public RoverResponseDTO moveRover(RoverCommandDTO roverCommands) {
        Rover rover = roverRepository.getById(roverCommands.getId());
        String[] commandsArray = roverCommands.getCommands().replaceAll("\\s+", "").split("(?!^)");

        for (String command : commandsArray) {
            if (command.isEmpty()) {
                continue;
            }
            rover = roverMovement.moveRover(rover, command);
        }
        return roverMapper.roverMappedAfterCommands(roverRepository.save(rover));
    }

    public RoverGetSurfaceDTO addSurface(Long surfaceId, long id, boolean roverAlreadyAddedToSurface) {
        Rover rover = roverRepository.findById(id).map(roverFound -> {
            if (roverFound.getSurface() != null) {
                String errorMessage = roverFound.getSurface().getId() == surfaceId ? "This rover is already in this surface." : "There is already a surface on this rover.";
                throw new AlreadyExistsEntityException(errorMessage);
            }
            SurfaceGetCompleteDTO surface = surfaceService.getSurface(surfaceId);
            if (!isRoverPositionValid(surface, roverFound)) {
                throw new IllegalArgumentException("Rover " + roverFound.getId() + " position exceeds the surface boundaries.");
            }
            roverFound.setSurface(surfaceMapper.surfaceUpdateDTOToSurface(surface));
            if (!roverAlreadyAddedToSurface) {
                checkIfRoverAlreadyAdded(surfaceId, roverFound.getId());
            }
            return roverRepository.save(roverFound);
        }).orElseThrow(() -> new EntityNotFoundException("Rover not found: " + id));
        return roverMapper.roverToRoverSurfaceDTO(rover);
    }

    private boolean isRoverPositionValid(SurfaceGetCompleteDTO surface, Rover roverFound) {
        return (surface.getExtremeX() >= roverFound.getXPosition() && surface.getExtremeY() >= roverFound.getYPosition());
    }

    private void checkIfRoverAlreadyAdded(long surfaceId, long roverId) {
        SurfaceAddRoverDTO surfaceAddRoverDTO = new SurfaceAddRoverDTO();
        surfaceAddRoverDTO.setRoverIds(new ArrayList<Long>(Arrays.asList(roverId)));
        surfaceService.addRovers(surfaceAddRoverDTO, surfaceId, true);

    }
}
