package com.marsrovers.mappers;

import com.marsrovers.dtos.*;
import com.marsrovers.models.Rover;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoverMapper {

    // get Current Position
    RoverBasicDTO roverToRoverBasicDTO (Rover rover);

    // get Rover
    RoverGetCompleteDTO roverToRoverCompleteDTO (Rover rover);

    //get Surface from Rover
    RoverGetSurfaceDTO roverToRoverSurfaceDTO (Rover rover);

    // creating new rover from DTO
    Rover roverCreationDTOToRover (RoverCreationDTO roverCreationDTO);

    // update an existing Rover from a complete RoverDTO object
    Rover roverUpdateDTOToRover (RoverGetCompleteDTO roverGetCompleteDTO);

    List<RoverGetCompleteDTO> mapListRoverToListRoverCompleteDTO (List<Rover> rovers);

    RoverResponseDTO roverMappedAfterCommands (Rover rover);
}
