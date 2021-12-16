package com.marsrovers.services;

import com.marsrovers.dtos.RoverBasicDTO;
import com.marsrovers.dtos.RoverCreationDTO;
import com.marsrovers.dtos.RoverGetCompleteDTO;
import com.marsrovers.dtos.RoverGetSurfaceDTO;
import com.marsrovers.mappers.RoverMapper;
import com.marsrovers.models.Rover;
import com.marsrovers.repository.RoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoverService {

    @Autowired
    private RoverRepository roverRepository;

    @Autowired
    private RoverMapper roverMapper;

    @Autowired
    private SurfaceService surfaceService;

    public RoverGetCompleteDTO getRover(long id) {
        return roverRepository.findById(id).map(roverMapper::roverToRoverCompleteDTO).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Reservation not found.")
        );
    }

    public List<RoverGetCompleteDTO> getRovers() {
        return roverMapper.mapListRoverToListRoverCompleteDTO(roverRepository.findAll());
        // return roverRepository.findAll().stream().map(roverMapper::roverToRoverCompleteDTO).collect(Collectors.toList());
    }

    public RoverBasicDTO createRover(RoverCreationDTO roverCreationDTO) {
        return roverMapper.roverToRoverBasicDTO(roverRepository.saveAndFlush(roverMapper.roverCreationDTOToRover(roverCreationDTO)));
    }

    public RoverBasicDTO deleteRover(long id) {
        return roverMapper.roverToRoverBasicDTO(deleteRoverFromSurface(id));
    }

    private Rover deleteRoverFromSurface(long id) {
        return roverRepository.findById(id).map(rover -> {
            surfaceService.deleteRoverFromSurface(rover.getSurface(), rover);
            return roverRepository.save(rover);
        }).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Rover not found.")
        );
    }

    public RoverGetSurfaceDTO getSurfaceFromRover(long id) {
        return roverRepository.findById(id).map(roverMapper::roverToRoverSurfaceDTO).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Rover not found.")
        );
    }

    public RoverBasicDTO getCurrentPosition(long id) {
        return roverRepository.findById(id).map(roverMapper::roverToRoverBasicDTO).<EntityNotFoundException>orElseThrow(() ->
                new EntityNotFoundException("Rover not found.")
        );
    }
}
