package com.marsrovers.rovers.services;

import com.marsrovers.rovers.movement.RoverCommand;
import com.marsrovers.rovers.movement.RoverMovement;
import com.marsrovers.rovers.assembler.RoverModelAssembler;
import com.marsrovers.exceptions.AlreadyExistsEntityException;
import com.marsrovers.exceptions.EntityNotFoundException;
import com.marsrovers.rovers.dtos.*;
import com.marsrovers.rovers.mappers.RoverMapper;
import com.marsrovers.rovers.models.Rover;
import com.marsrovers.rovers.repository.RoverRepository;
import com.marsrovers.surfaces.services.SurfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if ((roverRepository.findByName(roverCreationDTO.getName()) == null) || roverRepository.existsById(roverCreationDTO.getId())) {
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
        String[] commandsArray = roverCommands.getCommands().split("\\s+");
        System.out.println(Arrays.toString(RoverCommand.values()));
        System.out.println(commandsArray);

        for (String command: commandsArray) {
            if(command.isEmpty()) {
                continue;
            }
            rover = roverMovement.moveRover(rover, command);
        }

        return roverMapper.roverMappedAfterCommands(roverRepository.save(rover));
    }

    public RoverGetSurfaceDTO addSurface(RoverGetSurfaceDTO roverDTO) {
        Rover rover = roverRepository.findById(roverDTO.getId()).map(roverFound -> {
            if (roverFound.getSurface() != null) {
                throw new AlreadyExistsEntityException("There is already a surface on this rover");
            }
            return roverRepository.save(roverMapper.roverSurfaceToRover(roverDTO));
        }).orElseThrow(() -> new EntityNotFoundException("Rover not found: " + roverDTO.getId()));
        return roverMapper.roverToRoverSurfaceDTO(rover);
    }
}
