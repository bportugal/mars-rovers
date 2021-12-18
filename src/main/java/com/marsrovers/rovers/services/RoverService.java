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
            throw new AlreadyExistsEntityException("Rover already created");
        }
        return roverMapper.roverToRoverBasicDTO(roverRepository.saveAndFlush(roverMapper.roverCreationDTOToRover(roverCreationDTO)));
    }

    public String deleteRover(long id) {
        return deleteRoverFromSurface(id);
    }

    public String deleteRoverFromSurface(long id) {
        return roverRepository.findById(id).map(rover -> {
            if (!surfaceService.canDeleteRoverFromSurface(rover)) {
                throw new EntityNotFoundException("Rover " + id + " not found in this surface.");
            }
            roverRepository.deleteById(rover.getId());
            return "Rover was deleted";
        }).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Rover not found to be deleted: " + id)
        );
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

        for (String command: commandsArray) {
            if(command.isEmpty()) {
                continue;
            }
            rover = roverMovement.moveRover(rover, command);
        }

        return roverMapper.roverMappedAfterCommands(roverRepository.save(rover));
    }

    public RoverGetSurfaceDTO addSurface(Long surfaceId, long id, boolean roverAlreadyAddedToSurface) {
        Rover rover = roverRepository.findById(id).map(roverFound -> {
            if (roverFound.getSurface() != null) {
                throw new AlreadyExistsEntityException("There is already a surface on this rover");
            }
            SurfaceGetCompleteDTO surface = surfaceService.getSurface(surfaceId);
            roverFound.setSurface(surfaceMapper.surfaceUpdateDTOToSurface(surface));
            if (!roverAlreadyAddedToSurface) {
                SurfaceAddRoverDTO surfaceAddRoverDTO = new SurfaceAddRoverDTO();
                surfaceAddRoverDTO.setRoverIds(new ArrayList<Long>(Arrays.asList(roverFound.getId())));
                surfaceService.addRovers(surfaceAddRoverDTO, surfaceId, true);
            }
            return roverRepository.save(roverFound);
        }).orElseThrow(() -> new EntityNotFoundException("Rover not found: " + id));
        return roverMapper.roverToRoverSurfaceDTO(rover);
    }
}
